package com.asierg.cqrspoc.conversation.application.create

import com.asierg.cqrspoc.shared.application.commandbus.Command
import java.util.*

data class CreateConversationCommand(
    val conversationId: UUID,
    val message: String,
) : Command()
