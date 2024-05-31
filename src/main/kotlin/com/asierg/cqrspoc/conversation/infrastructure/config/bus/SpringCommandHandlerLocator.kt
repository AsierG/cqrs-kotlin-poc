package com.asierg.cqrspoc.conversation.infrastructure.config.bus

import com.asierg.cqrspoc.shared.domain.bus.command.Command
import com.asierg.cqrspoc.shared.domain.bus.command.CommandBus
import com.asierg.cqrspoc.shared.domain.bus.command.CommandHandler

class SpringCommandHandlerLocator(commandHandlerImplementations: List<CommandHandler<out Command>>) : CommandBus {

    val handlers: Map<Class<Command>, CommandHandler<Command>> =
        commandHandlerImplementations.associate { commandHandler ->
            val commandClass = extractCommandClass(commandHandler)
            commandClass to commandHandler as CommandHandler<Command>
        }

    override fun dispatch(command: Command) {
        if (!handlers.containsKey(command.javaClass)) {
            throw CommandHandlerException(command.javaClass.name)
        }
        handlers[command.javaClass]!!.handle(command)
    }

    private fun extractCommandClass(handler: CommandHandler<*>): Class<Command> {
        return BusReflectionUtils.resolveGenericTypeResolverClass<Command>(
            handler,
            CommandHandler::class.java,
            0,
        ) as Class<Command>
    }
}
