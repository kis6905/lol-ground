package com.leaf.lolground.application.api.summoner

import com.leaf.lolground.application.annotations.ApiController
import com.leaf.lolground.domain.summoner.SummonerService
import com.leaf.lolground.domain.summoner.dto.SummonerDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@ApiController
class SummonerController(
    val summonerService: SummonerService,
) {

    @GetMapping("/summoner/{summonerName}")
    fun findSummoner(@PathVariable summonerName: String): ResponseEntity<SummonerDto?> {
        return summonerService.findSummonerInfo(summonerName)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }

}