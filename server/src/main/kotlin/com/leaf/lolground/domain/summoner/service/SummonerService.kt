package com.leaf.lolground.domain.summoner.service

import com.leaf.lolground.domain.summoner.dto.Summoner
import com.leaf.lolground.infrastructure.api.lol.SummonerApi
import com.leaf.lolground.infrastructure.helper.withBlockAndIOContext
import kotlinx.coroutines.*
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class SummonerService(
    val summonerApi: SummonerApi,
) {

    /*
     * TODO:
     *  circuit breaker 적용 테스트
     *  cache 적용
     */
    fun findSummoner(summonerName: String): Summoner = withBlockAndIOContext co@{
        val def = async { summonerApi.findSummoner(summonerName) }
        return@co def.await()
    }

}