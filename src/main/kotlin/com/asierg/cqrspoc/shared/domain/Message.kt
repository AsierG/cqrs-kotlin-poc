package com.asierg.cqrspoc.shared.domain

import java.time.Instant
import java.util.*

abstract class Message {
    private val messageID: UUID
    private val occurredOn: Instant

    constructor() {
        this.messageID = UUID.randomUUID()
        this.occurredOn = Instant.now()
    }

    constructor(messageID: UUID, occurredOn: Instant) {
        this.messageID = messageID
        this.occurredOn = occurredOn
    }

    abstract fun type(): MessageType

    fun messageID(): UUID {
        return this.messageID
    }

    fun occurredOn(): Instant {
        return this.occurredOn
    }
}
