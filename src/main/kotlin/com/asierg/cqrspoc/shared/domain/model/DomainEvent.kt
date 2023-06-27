package com.asierg.cqrspoc.shared.domain.model

import com.asierg.cqrspoc.shared.domain.DomainMessage
import com.asierg.cqrspoc.shared.domain.MessageType
import com.asierg.cqrspoc.shared.domain.model.valueObject.UuidBasedIdentity
import com.fasterxml.jackson.annotation.JsonAutoDetect
import java.time.Instant
import java.util.*

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
abstract class DomainEvent : DomainMessage {

    protected constructor(aggregateId: UuidBasedIdentity?) : super(aggregateId!!)

    protected constructor(aggregateId: UUID) : super(aggregateId)

    protected constructor() : super()

    protected constructor(aggregateId: UUID, messageId: UUID, occurredOn: Instant) :
        super(
            aggregateId,
            messageId,
            occurredOn,
        )

    override fun type(): MessageType {
        return MessageType.EVENT
    }
}
