package com.asierg.cqrspoc.conversation.domain.model

import com.asierg.cqrspoc.shared.domain.model.valueObject.UuidBasedIdentity
import java.util.*

data class ConversationMessageId(val value: UUID) : UuidBasedIdentity(value)
