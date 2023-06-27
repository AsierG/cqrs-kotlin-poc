package com.asierg.cqrspoc.shared.domain.model

import java.util.*

abstract class AggregateRoot {

    private var domainEvents: MutableList<DomainEvent> = ArrayList()

    abstract fun id(): AggregateId

    protected fun record(event: DomainEvent) {
        domainEvents.add(event)
    }

    fun recordedEvents(): List<DomainEvent> {
        val events: List<DomainEvent> = domainEvents
        domainEvents = ArrayList()
        return events
    }

    override fun hashCode(): Int {
        return Objects.hash(id())
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        return if (other !is AggregateRoot) {
            false
        } else id() == other.id()
    }
}
