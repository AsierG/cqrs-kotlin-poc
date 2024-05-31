package com.asierg.cqrspoc.shared.domain.bus.command

interface CommandBus {

    fun dispatch(command: Command)
}
