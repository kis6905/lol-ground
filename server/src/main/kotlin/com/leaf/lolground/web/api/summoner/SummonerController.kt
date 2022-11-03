package com.leaf.lolground.web.api.summoner

import com.leaf.lolground.domain.summoner.dto.Summoner
import com.leaf.lolground.domain.summoner.service.SummonerService
import mu.KotlinLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger {}

@RestController
class SummonerController(
    val summonerService: SummonerService,
) {

    @GetMapping("/summoner/{summonerName}")
    fun findSummoner(@PathVariable summonerName: String): Summoner {
        return summonerService.findSummoner(summonerName)
    }
}