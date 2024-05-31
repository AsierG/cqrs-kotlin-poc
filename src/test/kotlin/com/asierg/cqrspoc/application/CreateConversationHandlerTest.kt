package com.asierg.cqrspoc.conversation.application

import com.asierg.cqrspoc.conversation.application.create.CreateConversationCommand
import com.asierg.cqrspoc.conversation.application.create.CreateConversationHandler
import com.asierg.cqrspoc.conversation.domain.events.ConversationCreated
import com.asierg.cqrspoc.conversation.domain.model.Conversation
import com.asierg.cqrspoc.conversation.domain.repository.ConversationRepository
import com.asierg.cqrspoc.shared.domain.bus.event.EventBus
import com.asierg.cqrspoc.shared.domain.model.DomainEvent
import net.datafaker.Faker
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.*
import java.util.*

class CreateConversationHandlerTest {

    private lateinit var conversationRepository: ConversationRepository
    private lateinit var eventBus: EventBus

    private lateinit var createConversationHandler: CreateConversationHandler

    @BeforeEach
    fun setUp() {
        conversationRepository = mock()
        eventBus = mock()

        createConversationHandler = CreateConversationHandler(
            conversationRepository = conversationRepository,
            eventBus = eventBus,
        )
    }

    @Test
    fun `should create a conversation in a happy path`() {
        val conversationId = UUID.randomUUID()
        val text = Faker().lorem().sentence()
        val conversationCaptor: KArgumentCaptor<Conversation> = argumentCaptor()
        doNothing().`when`(conversationRepository).save(
            conversationCaptor.capture(),
        )
        val domainEventCaptor: KArgumentCaptor<List<DomainEvent>> = argumentCaptor()
        doNothing().`when`(eventBus).publish(
            domainEventCaptor.capture(),
        )

        createConversationHandler.handle(
            command = CreateConversationCommand(conversationId = conversationId, message = text),
        )

        `should invoke the repository and the eventBus only once`()
        `conversation has expected values`(
            conversation = conversationCaptor.firstValue,
            conversationIdValue = conversationId,
            messageTextValue = text,
        )
        `should publish only expected conversation created event`(
            domainEvents = domainEventCaptor.firstValue,
            conversationIdValue = conversationId,
            messageTextValue = text,
        )
    }

    private fun `should invoke the repository and the eventBus only once`() {
        verify(conversationRepository, times(1)).save(any())
        verify(eventBus, times(1)).publish(events = any())
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

    private fun `should publish only expected conversation created event`(
        domainEvents: List<DomainEvent>,
        conversationIdValue: UUID,
        messageTextValue: String,
    ) {
        Assertions.assertEquals(domainEvents.size, 1)
        val conversationCreated = domainEvents[0] as ConversationCreated
        Assertions.assertEquals(conversationCreated.aggregateId, conversationIdValue)
        Assertions.assertNotNull(conversationCreated.conversationMessageId)
        Assertions.assertEquals(conversationCreated.text, messageTextValue)
    }
}
