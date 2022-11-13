package com.leaf.lolground.application.api.match

import com.leaf.lolground.domain.match.MatchService
import com.leaf.lolground.domain.summoner.SummonerService
import com.leaf.lolground.domain.summoner.dto.SummonerDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class MatchController(
    val matchService: MatchService,
) {

    @GetMapping("/match/info/{puuid}")
    fun findMatchInfo(@PathVariable puuid: String): List<String> =
        matchService.findMatchIds(puuid)
}