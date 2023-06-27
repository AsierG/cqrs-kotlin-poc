package com.asierg.cqrspoc.conversation.domain.model

import com.asierg.cqrspoc.shared.domain.model.AggregateId
import java.util.*

data class ConversationId(val value: UUID) : AggregateId(value)
