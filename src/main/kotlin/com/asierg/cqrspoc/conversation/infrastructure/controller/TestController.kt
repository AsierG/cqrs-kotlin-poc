package com.asierg.cqrspoc.conversation.infrastructure.controller

import com.asierg.cqrspoc.shared.application.commandbus.CommandBus
import com.asierg.cqrspoc.shared.application.querybus.QueryBus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(private val commandBus: CommandBus, private val queryBus: QueryBus) {

    @PostMapping("/test")
    fun execute(): ResponseEntity<Void> {
        return ResponseEntity.ok().build()
    }
}
