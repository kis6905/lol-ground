package com.leaf.lolground.domain.summoner.service

import com.leaf.lolground.domain.summoner.dto.Summoner
import com.leaf.lolground.infrastructure.api.lol.SummonerApi
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class SummonerService(
    val summonerApi: SummonerApi,
) {

    /*
     * TODO:
     *  coroutine 적용
     *  circuit breaker 적용 테스트
     *  cache 적용
     */
    fun findSummoner(summonerName: String): Summoner {
        return summonerApi.findSummoner(summonerName)
    }

}