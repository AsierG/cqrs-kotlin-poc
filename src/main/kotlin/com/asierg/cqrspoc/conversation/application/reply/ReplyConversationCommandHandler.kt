package com.asierg.cqrspoc.conversation.application.reply

import com.asierg.cqrspoc.conversation.domain.model.*
import com.asierg.cqrspoc.conversation.domain.repository.ConversationRepository
import com.asierg.cqrspoc.shared.application.commandbus.CommandHandler
import com.asierg.cqrspoc.shared.application.eventbus.EventBus
import java.time.Instant
import java.util.*

class ReplyConversationCommandHandler(
    private val conversationRepository: ConversationRepository,
    private val eventBus: EventBus,
) :
    CommandHandler<ReplyConversationCommand> {

    override fun handle(command: ReplyConversationCommand) {
        val conversation = conversationRepository.find(ConversationId(command.conversationId))

        Instant.now().let { createdAt ->
            conversation.reply(
                Message(
                    conversationMessageId = ConversationMessageId(UUID.randomUUID()),
                    text = MessageText(command.message),
                    createdOn = CreatedOn(createdAt),
                ),
            )
        }

        conversationRepository.save(conversation = conversation)

        eventBus.publish(conversation)
    }
}
