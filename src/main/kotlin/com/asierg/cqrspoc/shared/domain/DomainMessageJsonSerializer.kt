package com.asierg.cqrspoc.shared.domain

class DomainMessageJsonSerializer {

    companion object {
        fun toMap(message: DomainMessage): Map<String, *> {
            return mapOf(
                "data" to mapOf(
                    "message_id" to message.messageID(),
                    "type" to message.javaClass.simpleName,
                    "occurred_on" to message.occurredOn(),
                    "attributes" to message.body(),
                ),
            )
        }
    }
}
