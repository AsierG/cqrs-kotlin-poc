package com.asierg.cqrspoc.conversation.domain.events

import com.asierg.cqrspoc.shared.domain.model.DomainEvent
import java.util.*

data class ConversationCreated(
    val aggregateId: UUID,
    val conversationMessageId: UUID,
    val text: String,
) : DomainEvent(aggregateId)
