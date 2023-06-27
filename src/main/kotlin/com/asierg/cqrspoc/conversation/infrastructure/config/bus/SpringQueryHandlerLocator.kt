package com.asierg.cqrspoc.conversation.infrastructure.config.bus

import com.asierg.cqrspoc.shared.application.querybus.Query
import com.asierg.cqrspoc.shared.application.querybus.QueryBus
import com.asierg.cqrspoc.shared.application.querybus.QueryHandler
import org.springframework.stereotype.Component
import java.lang.reflect.ParameterizedType

@Component
class SpringQueryHandlerLocator(queryHandlerImplementations: List<QueryHandler<*, *>>) : QueryBus {
    private val handlers: MutableMap<Class<*>?, QueryHandler<*, Query<*>>>

    init {
        handlers = HashMap()
        queryHandlerImplementations.forEach { queryHandler ->
            val queryClass = getQueryClass(queryHandler)
            handlers[queryClass] = queryHandler as QueryHandler<*, Query<*>>
        }
    }

    override fun <T> ask(query: Query<T>): T {
        if (!handlers.containsKey(query.javaClass)) {
            throw Exception(String.format("No handler for %s", query.javaClass.name))
        }
        return handlers[query.javaClass]!!.handle(query) as T
    }

    private fun getQueryClass(handler: QueryHandler<*, *>): Class<*>? {
        val queryInterface = (handler.javaClass.genericInterfaces[0] as ParameterizedType).actualTypeArguments[1]
        return Class.forName(queryInterface.typeName)
    }
}
