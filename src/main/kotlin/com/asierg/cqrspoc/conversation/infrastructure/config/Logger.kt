package com.asierg.cqrspoc.conversation.infrastructure.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class Logger {
    val logger: Logger = LoggerFactory.getLogger(this.javaClass)
}
