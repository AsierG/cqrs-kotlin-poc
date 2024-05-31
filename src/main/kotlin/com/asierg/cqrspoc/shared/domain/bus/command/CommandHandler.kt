package com.asierg.cqrspoc.shared.domain.bus.command

interface CommandHandler<T : Command> {

    fun handle(command: T)
}
