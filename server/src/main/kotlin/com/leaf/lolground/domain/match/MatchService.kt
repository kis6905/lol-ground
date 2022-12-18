package com.leaf.lolground.domain.match

import com.leaf.lolground.domain.match.dto.MatchDto
import com.leaf.lolground.domain.match.factory.MatchFactory
import com.leaf.lolground.infrastructure.api.lol.match.MatchApi
import com.leaf.lolground.infrastructure.api.lol.match.dto.Match
import com.leaf.lolground.infrastructure.helper.withBlockAndIOContext
import kotlinx.coroutines.async
import mu.KotlinLogging
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class MatchService(
    val matchApi: MatchApi,
    val matchFactory: MatchFactory,
) {

    @Cacheable(value = ["matchInfo"], key = "#puuid", unless = "#result == null")
    fun findMatchInfo(puuid: String): MatchDto = withBlockAndIOContext co@{
        val matchIdListDef = async { matchApi.findMatchIdList(puuid) }
        val matchIdList = matchIdListDef.await()

        val matchList: List<Match?> = matchIdList.map {
            Thread.sleep(250) // Riot API Limit 으로 인해 sleep
            val matchDef = async { matchApi.findMatch(it) }
            matchDef.await()
        }

        val playedGameCountOfThisWeek = matchList
            .filterNotNull()
            .count { it.matchInfo.isThisWeek() }

        val recentMatches = matchList
            .filterNotNull()
            .map { matchFactory.createRecentMatch(it.matchInfo, puuid) }

        return@co MatchDto(
            playedGameCountOfThisWeek = playedGameCountOfThisWeek,
            recentMatches = recentMatches,
        )
    }

    @CacheEvict(value = ["matchInfo"], key = "#puuid", allEntries = false)
    fun evictMatchInfo(puuid: String) {
        logger.info("Evict cache: matchInfo, puuid: $puuid")
    }

}