package com.leaf.lolground.domain.summoner.factory

import com.leaf.lolground.domain.summoner.dto.SummonerDto
import com.leaf.lolground.infrastructure.api.lol.league.constants.QueueType
import com.leaf.lolground.infrastructure.api.lol.league.dto.League
import com.leaf.lolground.infrastructure.api.lol.league.dto.findByQueueType
import com.leaf.lolground.infrastructure.api.lol.summoner.dto.Summoner
import org.springframework.stereotype.Component

@Component
class SummonerFactory {
    fun createSummonerDto(summoner: Summoner, leagueList: List<League>): SummonerDto {
        val soloLeague: League? = leagueList.findByQueueType(QueueType.RANKED_SOLO_5x5)
        val freeLeague: League? = leagueList.findByQueueType(QueueType.RANKED_FLEX_SR)

        return SummonerDto(
            summonerName = summoner.name,
            soloTier = soloLeague?.tier ?: "UnRanked",
            soloRank = soloLeague?.rank.orEmpty(),
            soloLeaguePoints = soloLeague?.leaguePoints ?: 0,
            soloWins = soloLeague?.wins ?: 0,
            soloLosses = soloLeague?.losses ?: 0,
            freeTier = freeLeague?.tier ?: "UnRanked",
            freeRank = freeLeague?.rank.orEmpty(),
            freeLeaguePoints = freeLeague?.leaguePoints ?: 0,
            freeWins = freeLeague?.wins ?: 0,
            freeLosses = freeLeague?.losses ?: 0
        )
    }
}