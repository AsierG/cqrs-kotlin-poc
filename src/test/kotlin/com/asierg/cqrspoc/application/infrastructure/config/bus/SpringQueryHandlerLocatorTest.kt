package com.asierg.cqrspoc.application.infrastructure.config.bus

import com.asierg.cqrspoc.conversation.application.find.GetConversationQuery
import com.asierg.cqrspoc.conversation.application.find.GetConversationQueryHandler
import com.asierg.cqrspoc.conversation.domain.model.Conversation
import com.asierg.cqrspoc.conversation.domain.mothers.ConversationMother
import com.asierg.cqrspoc.conversation.infrastructure.config.bus.QueryHandlerException
import com.asierg.cqrspoc.conversation.infrastructure.config.bus.SpringQueryHandlerLocator
import com.asierg.cqrspoc.shared.domain.bus.query.Query
import com.asierg.cqrspoc.shared.domain.bus.query.QueryHandler
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import java.util.*

class SpringQueryHandlerLocatorTest {

    private lateinit var springQueryHandlerLocator: SpringQueryHandlerLocator
    private lateinit var queryHandlers: List<QueryHandler<*, *>>
    private lateinit var getConversationQueryHandler: GetConversationQueryHandler
    private lateinit var query: Query<*>

    @BeforeEach
    fun setUp() {
        getConversationQueryHandler = mock()
        queryHandlers = listOf(getConversationQueryHandler)
        springQueryHandlerLocator = SpringQueryHandlerLocator(queryHandlers)
        query = mock(Query::class.java)
    }

    @Test
    fun `ask should return result when handler is found`() {
        val conversationResponse = ConversationMother.random()
        val query = GetConversationQuery(UUID.randomUUID())
        `when`(getConversationQueryHandler.handle(query)).thenReturn(conversationResponse)

        val result = springQueryHandlerLocator.ask(query)

        assertEquals(conversationResponse, result)
    }

    @Test
    fun `ask should throw QueryHandlerException when no handler is found`() {
        class GetConversationQueryTest : Query<Conversation>()

        val query = GetConversationQueryTest()

        assertThrows(QueryHandlerException::class.java) {
            springQueryHandlerLocator.ask(query)
        }
    }
}
