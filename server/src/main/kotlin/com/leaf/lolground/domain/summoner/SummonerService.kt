package com.leaf.lolground.domain.summoner

import com.leaf.lolground.domain.summoner.dto.SummonerDto
import com.leaf.lolground.domain.summoner.factory.SummonerFactory
import com.leaf.lolground.infrastructure.api.lol.league.LeagueApi
import com.leaf.lolground.infrastructure.api.lol.summoner.SummonerApi
import com.leaf.lolground.infrastructure.api.lol.summoner.dto.Summoner
import com.leaf.lolground.infrastructure.helper.withBlockAndIOContext
import kotlinx.coroutines.async
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class SummonerService(
    val summonerApi: SummonerApi,
    val leagueApi: LeagueApi,
    val summonerFactory: SummonerFactory,
) {

    fun findSummonerInfo(summonerName: String): SummonerDto = withBlockAndIOContext co@{
        val summonerDef = async { findSummonerWithCache(summonerName) }
        val summoner = summonerDef.await()

        val leagueListDef = async { leagueApi.findLeagueListBySummoner(summoner.id) }
        val leagueList = leagueListDef.await()

        return@co summonerFactory.createSummonerDto(summoner, leagueList)
    }

    suspend fun findSummonerWithCache(summonerName: String): Summoner =
        summonerApi.findSummonerWithCache(summonerName)

}