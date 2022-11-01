package com.leaf.lolground.web.api.summoner

import mu.KotlinLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger {}

@RestController
class SummonerController {

    @GetMapping("/hello")
    fun hello(): String {
        logger.debug { "This is debug log" }
        logger.info { "This is info log" }
        logger.warn { "This is warn log" }
        logger.error { "This is error log" }
        return "hello world"
    }
}