package com.leaf.lolground.domain.match

import com.leaf.lolground.domain.match.dto.MatchDto
import com.leaf.lolground.domain.match.factory.MatchFactory
import com.leaf.lolground.infrastructure.api.lol.match.MatchApi
import com.leaf.lolground.infrastructure.api.lol.match.dto.Match
import com.leaf.lolground.infrastructure.helper.withBlockAndIOContext
import kotlinx.coroutines.async
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class MatchService(
    val matchApi: MatchApi,
    val matchFactory: MatchFactory,
) {

    fun findMatchIds(puuid: String): MatchDto = withBlockAndIOContext co@{
        val matchIdListDef = async { matchApi.findMatchIdList(puuid) }
        val matchIdList = matchIdListDef.await()

        val matchList: List<Match?> = matchIdList.map {
            val matchDef = async { findMatchWithCache(it) }
            matchDef.await()
        }

        val playedGameCountOfThisWeek = matchList
            .filterNotNull()
            .count { it.info.isThisWeek() }

        val recentMatches = matchList
            .filterNotNull()
            .map { matchFactory.createRecentMatch(it.info, puuid) }

        return@co MatchDto(
            playedGameCountOfThisWeek = playedGameCountOfThisWeek,
            recentMatches = recentMatches,
        )
    }

    suspend fun findMatchWithCache(matchId: String): Match? =
        matchApi.findMatchWithCache(matchId)

}