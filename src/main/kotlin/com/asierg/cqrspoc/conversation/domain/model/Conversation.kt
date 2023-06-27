package com.asierg.cqrspoc.conversation.domain.model

import com.asierg.cqrspoc.conversation.domain.events.ConversationCreated
import com.asierg.cqrspoc.conversation.domain.events.ConversationReplied
import com.asierg.cqrspoc.shared.domain.model.AggregateId
import com.asierg.cqrspoc.shared.domain.model.AggregateRoot
import java.time.Instant
import java.util.*

data class Conversation(
    val conversationId: ConversationId,
    val createdOn: CreatedOn,
    val messages: MutableList<Message>,

) : AggregateRoot() {

    override fun id(): AggregateId {
        return this.conversationId
    }

    fun conversationIdValue(): UUID {
        return conversationId.value
    }

    fun createdOnValue(): Instant {
        return createdOn.value()
    }

    fun reply(message: Message) {
        this.messages.add(message)
        this.record(
            ConversationReplied(
                aggregateId = this.conversationId.value,
                conversationMessageId = message.conversationMessageId.value,
                text = message.text.value(),
            ),
        )
    }

    companion object {
        fun create(
            id: ConversationId,
            text: MessageText,
        ): Conversation = Instant.now().let { createdAt ->
            val messageId = UUID.randomUUID()
            val conversation = Conversation(
                conversationId = id,
                createdOn = CreatedOn(createdAt),
                messages = mutableListOf(
                    Message(
                        conversationMessageId = ConversationMessageId(messageId),
                        text = text,
                        createdOn = CreatedOn(createdAt),
                    ),
                ),
            )
            conversation.record(
                ConversationCreated(
                    aggregateId = id.value,
                    conversationMessageId = messageId,
                    text = text.value(),
                ),
            )
            return conversation
        }
    }
}
