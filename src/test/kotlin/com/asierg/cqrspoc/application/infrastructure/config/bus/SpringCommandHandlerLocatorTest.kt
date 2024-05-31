package com.asierg.cqrspoc.application.infrastructure.config.bus

import com.asierg.cqrspoc.conversation.application.create.CreateConversationCommand
import com.asierg.cqrspoc.conversation.application.create.CreateConversationHandler
import com.asierg.cqrspoc.conversation.infrastructure.config.bus.CommandHandlerException
import com.asierg.cqrspoc.conversation.infrastructure.config.bus.SpringCommandHandlerLocator
import com.asierg.cqrspoc.shared.domain.bus.command.Command
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class SpringCommandHandlerLocatorTest {

    @Test
    fun `dispatch correctly dispatches command`() {
        val command = mock(CreateConversationCommand::class.java)
        val handler = mock(CreateConversationHandler::class.java)
        val locator = SpringCommandHandlerLocator(listOf(handler))

        locator.dispatch(command)

        verify(handler).handle(command)
    }

    @Test
    fun `dispatch throws CommandHandlerException when no handler for command`() {
        val command = mock(Command::class.java)
        val locator = SpringCommandHandlerLocator(emptyList())

        assertThrows(CommandHandlerException::class.java) {
            locator.dispatch(command)
        }
    }
}
