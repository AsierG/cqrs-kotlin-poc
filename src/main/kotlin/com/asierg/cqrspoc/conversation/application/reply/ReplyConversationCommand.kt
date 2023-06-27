package com.asierg.cqrspoc.conversation.application.reply

import com.asierg.cqrspoc.shared.application.commandbus.Command
import java.util.*

data class ReplyConversationCommand(
    val conversationId: UUID,
    val message: String,
) : Command()
