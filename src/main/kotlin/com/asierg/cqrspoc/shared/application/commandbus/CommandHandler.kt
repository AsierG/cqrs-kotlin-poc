package com.asierg.cqrspoc.shared.application.commandbus

interface CommandHandler<T : Command> {

    fun handle(command: T)
}
