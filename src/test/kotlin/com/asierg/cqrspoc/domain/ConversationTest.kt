package com.asierg.cqrspoc.conversation.domain

import com.asierg.cqrspoc.conversation.domain.events.ConversationCreated
import com.asierg.cqrspoc.conversation.domain.events.ConversationReplied
import com.asierg.cqrspoc.conversation.domain.model.Conversation
import com.asierg.cqrspoc.conversation.domain.model.ConversationId
import com.asierg.cqrspoc.conversation.domain.model.Message
import com.asierg.cqrspoc.conversation.domain.model.MessageText
import com.asierg.cqrspoc.conversation.domain.mothers.ConversationMother
import com.asierg.cqrspoc.conversation.domain.mothers.MessageMother
import net.datafaker.Faker
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*

class ConversationTest {

    @Test
    fun `should create a conversation with expected values and record ConversationCreated event when a conversation is created`() {
        val conversationIdValue = UUID.randomUUID()
        val messageTextValue = Faker().lorem().sentence()

        val conversation = Conversation.create(
            id = ConversationId(conversationIdValue),
            text = MessageText(messageTextValue),
        )

        `conversation has expected values`(
            conversation = conversation,
            conversationIdValue = conversationIdValue,
            messageTextValue = messageTextValue,
        )
        `should record only expected conversation created event`(
            conversation = conversation,
            conversationIdValue = conversationIdValue,
            messageTextValue = messageTextValue,
        )
    }

    private fun `conversation has expected values`(
        conversation: Conversation,
        conversationIdValue: UUID,
        messageTextValue: String,
    ) {
        Assertions.assertEquals(conversation.id().uuid(), conversationIdValue)
        Assertions.assertEquals(conversation.messages.size, 1)
        Assertions.assertEquals(conversation.messages[0].text.value(), messageTextValue)
    }

    private fun `should record only expected conversation created event`(
        conversation: Conversation,
        conversationIdValue: UUID,
        messageTextValue: String,
    ) {
        val domainEvents = conversation.recordedEvents()
        Assertions.assertEquals(domainEvents.size, 1)
        val conversationCreated = domainEvents[0] as ConversationCreated
        Assertions.assertEquals(conversationCreated.aggregateId, conversationIdValue)
        Assertions.assertNotNull(conversationCreated.conversationMessageId)
        Assertions.assertEquals(conversationCreated.text, messageTextValue)
    }

    @Test
    fun `should add a message when conversation is replied`() {
        val conversation = ConversationMother.random()
        val message = MessageMother.random()

        conversation.reply(message)

        `the message is added to the conversation`(
            conversation = conversation,
            message = message,
        )
        `should record only expected conversation replied event`(
            conversation = conversation,
            conversationIdValue = conversation.conversationIdValue(),
            messageTextValue = message.textValue(),
        )
    }

    private fun `the message is added to the conversation`(
        conversation: Conversation,
        message: Message,
    ) {
        Assertions.assertTrue(conversation.messages.contains(message))
    }

    private fun `should record only expected conversation replied event`(
        conversation: Conversation,
        conversationIdValue: UUID,
        messageTextValue: String,
    ) {
        val domainEvents = conversation.recordedEvents()
        Assertions.assertEquals(domainEvents.size, 1)
        val conversationReplied = domainEvents[0] as ConversationReplied
        Assertions.assertEquals(conversationReplied.aggregateId, conversationIdValue)
        Assertions.assertNotNull(conversationReplied.conversationMessageId)
        Assertions.assertEquals(conversationReplied.text, messageTextValue)
    }
}
