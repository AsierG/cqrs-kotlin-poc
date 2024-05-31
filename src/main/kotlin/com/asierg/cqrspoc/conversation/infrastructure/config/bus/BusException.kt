package com.asierg.cqrspoc.conversation.infrastructure.config.bus

open class BusException(s: String) : RuntimeException(s)

class CommandHandlerException(commandClass: String) : BusException("No handler for $commandClass")

class QueryHandlerException(queryClass: String) : BusException("No handler for $queryClass")

class ProjectionHandlerException(projectionClass: String) : BusException("No handler for $projectionClass")
