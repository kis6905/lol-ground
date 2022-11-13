package com.leaf.lolground.domain.match

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
) {

    fun findMatchIds(puuid: String): List<String> = withBlockAndIOContext co@{
        val matchIdListDef = async { matchApi.findMatchIdList(puuid) }
        val matchIdList = matchIdListDef.await()

        matchIdList.forEach {
            val matchDef = async { findMatchWithCache(it) }
            val match = matchDef.await()
            logger.info("match: $match")
        }

        return@co matchIdList
    }

    suspend fun findMatchWithCache(matchId: String): Match =
        matchApi.findMatchWithCache(matchId)

}