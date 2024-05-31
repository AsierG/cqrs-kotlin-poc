package com.asierg.cqrspoc

import com.asierg.cqrspoc.conversation.application.create.CreateConversationCommand
import com.asierg.cqrspoc.conversation.application.find.GetConversationQuery
import com.asierg.cqrspoc.conversation.application.reply.ReplyConversationCommand
import com.asierg.cqrspoc.shared.domain.bus.command.CommandBus
import com.asierg.cqrspoc.shared.domain.bus.query.QueryBus
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.*

@SpringBootApplication
class CqrsKotlinPocApplication(
    private val commandBus: CommandBus,
    private val queryBus: QueryBus,
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        val conversationId = UUID.randomUUID()

        commandBus.dispatch(
            CreateConversationCommand(
                conversationId = conversationId,
                message = "my first message",
            ),
        )

        val conversation = queryBus.ask(GetConversationQuery(conversationId = conversationId))

        commandBus.dispatch(
            ReplyConversationCommand(
                conversationId = conversationId,
                message = "message replied",
            ),
        )
    }
}

fun main(args: Array<String>) {
    runApplication<CqrsKotlinPocApplication>(*args)
}
