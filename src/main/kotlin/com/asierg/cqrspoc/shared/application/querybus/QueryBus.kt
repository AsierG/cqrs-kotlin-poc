package com.asierg.cqrspoc.shared.application.querybus

interface QueryBus {

    fun <T> ask(query: Query<T>): T
}
