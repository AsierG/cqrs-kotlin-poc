package com.asierg.cqrspoc.conversation.domain.model

import java.time.Instant
import java.util.*

data class Message(
    val conversationMessageId: ConversationMessageId,
    val text: MessageText,
    val createdOn: CreatedOn,
) {

    fun messageIdIdValue(): UUID {
        return conversationMessageId.value
    }

    fun textValue(): String {
        return text.value()
    }

    fun createdOnValue(): Instant {
        return createdOn.value()
    }
}
