package com.asierg.cqrspoc.conversation.infrastructure.config.bus

import com.asierg.cqrspoc.shared.domain.bus.projection.Projection
import com.asierg.cqrspoc.shared.domain.bus.projection.ProjectionBus
import com.asierg.cqrspoc.shared.domain.bus.projection.ProjectionHandler

class SpringProjectionHandlerLocator(projectionHandlerImplementations: List<ProjectionHandler<*>>) : ProjectionBus {

    private val handlers: Map<Class<*>, ProjectionHandler<Projection>>

    init {
        handlers = HashMap()
        projectionHandlerImplementations.forEach { projectionHandler ->
            val projectionClass = extractProjectionClass(projectionHandler)
            handlers[projectionClass] = projectionHandler as ProjectionHandler<Projection>
        }
    }

    override fun project(projection: Projection) {
        if (!handlers.containsKey(projection.javaClass)) {
            throw ProjectionHandlerException(projection.javaClass.name)
        }
        handlers[projection.javaClass]!!.handle(projection)
    }

    private fun extractProjectionClass(handler: ProjectionHandler<*>): Class<*> {
        val value = extractProjection(handler)
        return Class.forName(value!!.typeName)
    }

    private fun extractProjection(handler: ProjectionHandler<out Projection>): Class<out Projection?>? {
        return BusReflectionUtils.resolveGenericTypeResolverClass(
            handler,
            ProjectionHandler::class.java,
            0,
        )
    }
}
