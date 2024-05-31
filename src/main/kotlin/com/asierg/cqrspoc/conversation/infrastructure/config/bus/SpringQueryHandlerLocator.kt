package com.asierg.cqrspoc.conversation.infrastructure.config.bus

import com.asierg.cqrspoc.shared.domain.bus.query.Query
import com.asierg.cqrspoc.shared.domain.bus.query.QueryBus
import com.asierg.cqrspoc.shared.domain.bus.query.QueryHandler
import org.springframework.stereotype.Component
import java.lang.reflect.ParameterizedType

@Component
class SpringQueryHandlerLocator(queryHandlerImplementations: List<QueryHandler<*, *>>) : QueryBus {

    private val handlers: Map<Class<*>?, QueryHandler<*, Query<*>>> =
        queryHandlerImplementations.filterIsInstance<QueryHandler<*, Query<*>>>().associateBy { getQueryClass(it) }

    override fun <T> ask(query: Query<T>): T {
        if (!handlers.containsKey(query.javaClass)) {
            throw QueryHandlerException(query.javaClass.name)
        }
        return handlers[query.javaClass]!!.handle(query) as T
    }

    private fun getQueryClass(handler: QueryHandler<*, *>): Class<*>? {
        val queryInterface = (handler.javaClass.genericInterfaces[0] as ParameterizedType).actualTypeArguments[1]
        return Class.forName(queryInterface.typeName)
    }
}
