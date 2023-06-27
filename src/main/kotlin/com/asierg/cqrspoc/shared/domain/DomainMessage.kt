package com.asierg.cqrspoc.shared.domain

import com.asierg.cqrspoc.shared.domain.model.valueObject.UuidBasedIdentity
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.io.Serializable
import java.time.Instant
import java.util.*

@JsonIgnoreProperties(allowSetters = true, value = ["body", "message_id", "occurred_on"])
abstract class DomainMessage : Message {

    private val aggregateId: UUID

    private var body: Map<String, Serializable>? = null

    constructor(aggregateId: UUID) : super() {
        this.aggregateId = aggregateId
    }

    protected constructor() : this(UUID.randomUUID())

    protected constructor(aggregateId: UuidBasedIdentity) : this(aggregateId.uuid())

    protected constructor(aggregateId: UUID, messageId: UUID, occurredOn: Instant) : super(
        messageId,
        occurredOn,
    ) {
        this.aggregateId = aggregateId
    }

    fun aggregateId(): UUID {
        return aggregateId
    }

    fun body(): Map<String, Serializable> {
        if (null == body) {
            body = initBody(this)
        }
        return body as Map<String, Serializable>
    }

    private fun initBody(domainMessage: DomainMessage): Map<String, Serializable> {
        return JacksonObjectMapper.convertValue(domainMessage, MutableMap::class.java) as Map<String, Serializable>
    }
}
