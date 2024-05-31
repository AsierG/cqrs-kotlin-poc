package com.asierg.cqrspoc.conversation.infrastructure.config.dependencies

import com.asierg.cqrspoc.conversation.application.create.CreateConversationHandler
import com.asierg.cqrspoc.conversation.application.find.GetConversationQueryHandler
import com.asierg.cqrspoc.conversation.application.reply.ReplyConversationCommandHandler
import com.asierg.cqrspoc.conversation.domain.repository.ConversationRepository
import com.asierg.cqrspoc.shared.domain.bus.event.EventBus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DependencyInjectionConf {

    @Bean
    fun createConversationCommandHandler(
        conversationRepository: ConversationRepository,
        eventBus: EventBus,
    ) =
        CreateConversationHandler(
            conversationRepository = conversationRepository,
            eventBus = eventBus,
        )

    @Bean
    fun replyConversationCommandHandler(
        conversationRepository: ConversationRepository,
        eventBus: EventBus,
    ) =
        ReplyConversationCommandHandler(
            conversationRepository = conversationRepository,
            eventBus = eventBus,
        )

    @Bean
    fun getConversationQueryHandler(
        conversationRepository: ConversationRepository,
    ) =
        GetConversationQueryHandler(conversationRepository = conversationRepository)
}
