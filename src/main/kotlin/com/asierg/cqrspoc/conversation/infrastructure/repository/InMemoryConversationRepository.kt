package com.asierg.cqrspoc.conversation.infrastructure.repository

import com.asierg.cqrspoc.conversation.domain.exceptions.ConversationNotFoundException
import com.asierg.cqrspoc.conversation.domain.model.Conversation
import com.asierg.cqrspoc.conversation.domain.model.ConversationId
import com.asierg.cqrspoc.conversation.domain.repository.ConversationRepository
import org.springframework.stereotype.Component

@Component
class InMemoryConversationRepository : ConversationRepository {

    val conversations: MutableMap<ConversationId, Conversation> = java.util.HashMap()

    override fun save(conversation: Conversation) {
        conversations[conversation.conversationId] = conversation
    }

    override fun find(id: ConversationId): Conversation {
        return conversations[id] ?: throw ConversationNotFoundException(id)
    }
}
