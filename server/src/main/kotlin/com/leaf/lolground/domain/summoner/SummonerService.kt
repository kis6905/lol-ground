package com.leaf.lolground.domain.summoner

import com.leaf.lolground.domain.summoner.dto.SummonerDto
import com.leaf.lolground.domain.summoner.factory.SummonerFactory
import com.leaf.lolground.infrastructure.api.lol.league.LeagueApi
import com.leaf.lolground.infrastructure.api.lol.league.dto.League
import com.leaf.lolground.infrastructure.api.lol.summoner.SummonerApi
import com.leaf.lolground.infrastructure.api.lol.summoner.dto.Summoner
import com.leaf.lolground.infrastructure.helper.withBlockAndIOContext
import kotlinx.coroutines.async
import mu.KotlinLogging
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class SummonerService(
    val summonerApi: SummonerApi,
    val leagueApi: LeagueApi,
    val summonerFactory: SummonerFactory,
) {

    fun findSummonerInfo(summonerName: String): SummonerDto = withBlockAndIOContext co@{
        val summonerDef = async { findSummoner(summonerName) }
        val summoner = summonerDef.await()

        val leagueListDef = async { findLeagueListBySummoner(summoner.id) }
        val leagueList = leagueListDef.await()

        return@co summonerFactory.createSummonerDto(summoner, leagueList)
    }

    suspend fun findSummoner(summonerName: String): Summoner {
        return summonerApi.findSummoner(summonerName)
    }

    suspend fun findLeagueListBySummoner(puuid: String): List<League> {
        return leagueApi.findLeagueListBySummoner(puuid)
    }

}