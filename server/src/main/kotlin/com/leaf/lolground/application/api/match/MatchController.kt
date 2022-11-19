package com.leaf.lolground.application.api.match

import com.leaf.lolground.application.annotations.ApiController
import com.leaf.lolground.domain.match.MatchService
import com.leaf.lolground.domain.match.dto.MatchDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@ApiController
class MatchController(
    val matchService: MatchService,
) {

    @GetMapping("/match/info/{puuid}")
    fun findMatchInfo(@PathVariable puuid: String): MatchDto =
        matchService.findMatchInfo(puuid)
}