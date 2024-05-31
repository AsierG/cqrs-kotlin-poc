package com.asierg.cqrspoc.conversation.application.create

import com.asierg.cqrspoc.conversation.domain.model.Conversation
import com.asierg.cqrspoc.conversation.domain.model.ConversationId
import com.asierg.cqrspoc.conversation.domain.model.MessageText
import com.asierg.cqrspoc.conversation.domain.repository.ConversationRepository
import com.asierg.cqrspoc.shared.domain.bus.command.CommandHandler
import com.asierg.cqrspoc.shared.domain.bus.event.EventBus

class CreateConversationHandler(
    private val conversationRepository: ConversationRepository,
    private val eventBus: EventBus,
) :
    CommandHandler<CreateConversationCommand> {

    override fun handle(command: CreateConversationCommand) {
        Conversation.create(
            id = ConversationId(command.conversationId),
            text = MessageText(command.message),
        ).let {
            conversationRepository.save(it)

            eventBus.publish(it.recordedEvents())
        }
    }
}
