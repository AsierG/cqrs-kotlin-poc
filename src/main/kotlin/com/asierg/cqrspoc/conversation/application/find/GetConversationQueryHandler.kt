package com.asierg.cqrspoc.conversation.application.find

import com.asierg.cqrspoc.conversation.domain.model.Conversation
import com.asierg.cqrspoc.conversation.domain.model.ConversationId
import com.asierg.cqrspoc.conversation.domain.repository.ConversationRepository
import com.asierg.cqrspoc.shared.application.querybus.QueryHandler

class GetConversationQueryHandler(private val conversationRepository: ConversationRepository) :
    QueryHandler<Conversation, GetConversationQuery> {

    override fun handle(query: GetConversationQuery): Conversation {
        return conversationRepository.find(ConversationId(query.conversationId))
    }
}
