package com.asierg.cqrspoc.shared.domain.model.valueObject

import java.util.*

abstract class UuidBasedIdentity(value: UUID) {

    private val value: UUID

    init {
        this.value = value
        println("Identifier Abstract Class. init block. Id is $value")
    }

    open fun id(): String {
        return this.value.toString()
    }

    open fun uuid(): UUID {
        return this.value
    }
}
