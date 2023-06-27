package com.asierg.cqrspoc.conversation.domain.mothers

import com.asierg.cqrspoc.conversation.domain.model.Conversation
import com.asierg.cqrspoc.conversation.domain.model.ConversationId
import com.asierg.cqrspoc.conversation.domain.model.CreatedOn
import java.time.Instant
import java.util.*

object ConversationMother {

    fun random() = Conversation(
        conversationId = ConversationId(UUID.randomUUID()),
        createdOn = CreatedOn(Instant.now()),
        messages = mutableListOf(MessageMother.random()),
    )
}
