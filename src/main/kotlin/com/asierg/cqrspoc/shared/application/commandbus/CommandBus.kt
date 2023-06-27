package com.asierg.cqrspoc.shared.application.commandbus

interface CommandBus {

    fun dispatch(command: Command)
}
