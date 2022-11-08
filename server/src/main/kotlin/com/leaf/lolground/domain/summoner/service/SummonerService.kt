package com.leaf.lolground.domain.summoner.service

import com.leaf.lolground.domain.summoner.dto.Summoner
import com.leaf.lolground.infrastructure.api.lol.SummonerApi
import com.leaf.lolground.infrastructure.helper.withBlockAndIOContext
import kotlinx.coroutines.*
import mu.KotlinLogging
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class SummonerService(
    val summonerApi: SummonerApi,
) {

    @Cacheable(value = ["summoner"], key = "#summonerName")
    fun findSummoner(summonerName: String): Summoner = withBlockAndIOContext co@{
        val def = async { summonerApi.findSummoner(summonerName) }
        return@co def.await()
    }

    @Scheduled(fixedRateString = (1000 * 60 * 60).toString())
    @CacheEvict(value = ["summoner"], allEntries = true)
    fun evictSummoner(): Unit {
        logger.info("Evict cache: summoner")
    }

}