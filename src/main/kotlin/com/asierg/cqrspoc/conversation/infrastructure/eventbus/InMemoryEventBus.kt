package com.asierg.cqrspoc.conversation.infrastructure.eventbus

import com.asierg.cqrspoc.shared.application.eventbus.EventBus
import com.asierg.cqrspoc.shared.domain.DomainMessageJsonSerializer
import com.asierg.cqrspoc.shared.domain.model.DomainEvent
import mu.KLogging
import org.springframework.stereotype.Component

@Component
class InMemoryEventBus() : EventBus {

    private val events = mutableListOf<DomainEvent>()

    private companion object : KLogging()

    override fun publish(events: List<DomainEvent>) {
        this.events += events
        this.events.forEach { event ->
            logger.info("Event: ${DomainMessageJsonSerializer.toMap(event)}")
        }
        flush()
    }

    override fun get(): List<DomainEvent> {
        return events
    }

    override fun flush() {
        events.clear()
    }
}
