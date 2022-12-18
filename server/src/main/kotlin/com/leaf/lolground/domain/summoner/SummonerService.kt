package com.leaf.lolground.domain.summoner

import com.leaf.lolground.domain.summoner.dto.SummonerDto
import com.leaf.lolground.domain.summoner.factory.SummonerFactory
import com.leaf.lolground.infrastructure.api.lol.league.LeagueApi
import com.leaf.lolground.infrastructure.api.lol.summoner.SummonerApi
import com.leaf.lolground.infrastructure.cache.CacheHelper
import com.leaf.lolground.infrastructure.helper.withBlockAndIOContext
import kotlinx.coroutines.async
import mu.KotlinLogging
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class SummonerService(
    val summonerApi: SummonerApi,
    val leagueApi: LeagueApi,
    val summonerFactory: SummonerFactory,
    val cacheHelper: CacheHelper,
) {

    companion object {
        const val CACHE_NAME_SPACE: String = "summonerInfo:"
    }

    @Cacheable(value = ["summonerInfo"], key = "#summonerName", unless = "#result == null")
    fun findSummonerInfo(summonerName: String): SummonerDto? = withBlockAndIOContext co@{
        val summonerDef = async { summonerApi.findSummoner(summonerName) }
        val summoner = summonerDef.await()

        summoner?.apply {
            val leagueListDef = async { leagueApi.findLeagueListBySummoner(summoner.id) }
            val leagueList = leagueListDef.await()
            cacheHelper.setCachedTime(buildCachedTimeKey(summonerName))
            return@co summonerFactory.createSummonerDto(summoner, leagueList)
        }
        return@co null
    }

    @CacheEvict(value = ["summonerInfo"], key = "#summonerName", allEntries = false)
    fun evictSummonerInfo(summonerName: String) {
        logger.info("Evict cache: summonerInfo, summonerName: $summonerName")
    }

    fun buildCachedTimeKey(postFix: String): String =
        CACHE_NAME_SPACE + postFix

}