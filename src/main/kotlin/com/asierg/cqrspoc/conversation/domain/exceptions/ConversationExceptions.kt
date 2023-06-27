package com.asierg.cqrspoc.conversation.domain.exceptions

import com.asierg.cqrspoc.conversation.domain.model.ConversationId

sealed class InvalidArgumentConversationException(override val message: String, override val cause: Throwable? = null) :
    IllegalArgumentException(message, cause)

data class InvalidConversationIdException(val id: String, override val cause: Throwable?) :
    InvalidArgumentConversationException("The id <$id> is not a valid conversation id", cause)

data class InvalidTextException(val name: String) :
    InvalidArgumentConversationException("The text <$name> is not a valid text")

sealed class ConversationException(override val message: String, override val cause: Throwable? = null) :
    RuntimeException(message, cause)

data class ConversationNotFoundException(val id: ConversationId) :
    ConversationException("There is no conversation with id <${id.value}>")
