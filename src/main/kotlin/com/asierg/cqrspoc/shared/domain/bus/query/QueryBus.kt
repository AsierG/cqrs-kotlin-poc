package com.asierg.cqrspoc.shared.domain.bus.query

interface QueryBus {

    fun <T> ask(query: Query<T>): T
}
