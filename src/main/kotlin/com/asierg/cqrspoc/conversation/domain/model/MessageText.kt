package com.asierg.cqrspoc.conversation.domain.model

import com.asierg.cqrspoc.conversation.domain.exceptions.InvalidTextException
import com.asierg.cqrspoc.shared.domain.model.valueObject.StringValueObject

data class MessageText(private val value: String) : StringValueObject(value) {

    init {
        validate()
    }

    private fun validate() {
        if (value.isEmpty() || value.isBlank()) {
            throw InvalidTextException(value)
        }
    }
}
