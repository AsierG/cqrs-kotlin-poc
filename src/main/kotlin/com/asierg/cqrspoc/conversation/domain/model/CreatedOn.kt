package com.asierg.cqrspoc.conversation.domain.model

import com.asierg.cqrspoc.shared.domain.model.valueObject.InstantValueObject
import java.time.Instant

data class CreatedOn(private val value: Instant) : InstantValueObject(value)
