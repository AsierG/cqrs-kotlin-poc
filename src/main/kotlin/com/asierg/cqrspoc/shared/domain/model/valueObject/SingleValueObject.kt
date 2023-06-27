package com.asierg.cqrspoc.shared.domain.model.valueObject

abstract class SingleValueObject<T> protected constructor(private val value: T) {

    fun value(): T {
        return value
    }
}
