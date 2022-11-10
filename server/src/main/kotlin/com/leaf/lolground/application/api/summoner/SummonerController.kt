package com.leaf.lolground.application.api.summoner

import com.leaf.lolground.domain.summoner.SummonerService
import com.leaf.lolground.domain.summoner.dto.SummonerDto
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
    fun findSummoner(@PathVariable summonerName: String): SummonerDto =
        summonerService.findSummonerInfo(summonerName)
}