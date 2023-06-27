package com.asierg.cqrspoc.conversation.infrastructure.config.bus

import com.asierg.cqrspoc.shared.application.commandbus.Command
import com.asierg.cqrspoc.shared.application.commandbus.CommandBus
import com.asierg.cqrspoc.shared.application.commandbus.CommandHandler
import org.springframework.stereotype.Component
import java.lang.reflect.ParameterizedType

@Component
class SpringCommandHandlerLocator(commandHandlerImplementations: List<CommandHandler<*>>) : CommandBus {

    private val handlers: Map<Class<*>, CommandHandler<Command>>

    init {
        handlers = HashMap()
        commandHandlerImplementations.forEach { commandHandler ->
            val commandClass = getCommandClass(commandHandler)
            handlers[commandClass!!] = commandHandler as CommandHandler<Command>
        }
    }

    override fun dispatch(command: Command) {
        if (!handlers.containsKey(command.javaClass)) {
            throw Exception(String.format("No handler for %s", command.javaClass))
        }
        handlers[command.javaClass]!!.handle(command)
    }

    private fun getCommandClass(handler: CommandHandler<*>): Class<*>? {
        val commandInterface =
            (handler.javaClass.genericInterfaces[0] as ParameterizedType).actualTypeArguments[0]
        return Class.forName(commandInterface.typeName)
    }
}
