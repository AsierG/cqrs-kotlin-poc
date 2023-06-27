package com.asierg.cqrspoc.shared.application.querybus

interface QueryHandler<T, U : Query<T>> {

    fun handle(query: U): T
}
