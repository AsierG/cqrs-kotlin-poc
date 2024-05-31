package com.asierg.cqrspoc.shared.domain.bus.query

interface QueryHandler<T, U : Query<T>> {

    fun handle(query: U): T
}
