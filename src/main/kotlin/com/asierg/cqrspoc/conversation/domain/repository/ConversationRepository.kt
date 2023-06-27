package com.asierg.cqrspoc.conversation.domain.repository

import com.asierg.cqrspoc.conversation.domain.model.Conversation
import com.asierg.cqrspoc.conversation.domain.model.ConversationId

interface ConversationRepository {

    fun save(conversation: Conversation)

    fun find(id: ConversationId): Conversation
}
