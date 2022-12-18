package com.leaf.lolground.application.api.summoner

import com.leaf.lolground.application.annotations.ApiController
import com.leaf.lolground.domain.summoner.dto.SummonerDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@ApiController
class SummonerController(
    val summonerFacade: SummonerFacade,
) {

    @GetMapping("/summoner/{summonerName}")
    fun findSummoner(@PathVariable summonerName: String): ResponseEntity<SummonerDto?> {
        return summonerFacade.findSummonerInfo(summonerName)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }

    @DeleteMapping("/summoner/cache/{summonerName}")
    fun deleteCacheOfSummoner(@PathVariable summonerName: String): ResponseEntity<String> {
        summonerFacade.evictSummonerInfoAndMatchInfo(summonerName)
        return ResponseEntity.ok("")
    }

}