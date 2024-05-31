package com.asierg.cqrspoc.conversation.application.find

import com.asierg.cqrspoc.conversation.domain.model.Conversation
import com.asierg.cqrspoc.shared.domain.bus.query.Query
import java.util.UUID

data class GetConversationQuery(
    val conversationId: UUID,
) : Query<Conversation>()
