package com.asierg.cqrspoc.conversation.domain.mothers

import com.asierg.cqrspoc.conversation.domain.model.ConversationMessageId
import com.asierg.cqrspoc.conversation.domain.model.CreatedOn
import com.asierg.cqrspoc.conversation.domain.model.Message
import com.asierg.cqrspoc.conversation.domain.model.MessageText
import net.datafaker.Faker
import java.time.Instant
import java.util.*

object MessageMother {

    private val faker = Faker()

    fun random() = Message(
        conversationMessageId = ConversationMessageId(UUID.randomUUID()),
        text = MessageText(faker.lorem().sentence()),
        createdOn = CreatedOn(Instant.now()),
    )
}
