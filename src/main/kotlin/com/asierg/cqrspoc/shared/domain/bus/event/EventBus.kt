package com.asierg.cqrspoc.shared.domain.bus.event

import com.asierg.cqrspoc.shared.domain.model.AggregateRoot
import com.asierg.cqrspoc.shared.domain.model.DomainEvent

interface EventBus {

    fun publish(events: List<DomainEvent>)

    fun publish(aggregate: AggregateRoot) {
        publish(aggregate.recordedEvents())
    }

    fun get(): List<DomainEvent>

    fun flush()
}
