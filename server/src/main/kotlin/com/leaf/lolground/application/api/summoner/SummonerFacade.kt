package com.leaf.lolground.application.api.summoner

import com.leaf.lolground.domain.match.MatchService
import com.leaf.lolground.domain.summoner.SummonerService
import com.leaf.lolground.domain.summoner.dto.SummonerDto
import com.leaf.lolground.infrastructure.cache.CacheHelper
import com.leaf.lolground.infrastructure.helper.diffFormattedStringFromNow
import org.springframework.stereotype.Service

@Service
class SummonerFacade(
    val summonerService: SummonerService,
    val matchService: MatchService,
    val cacheHelper: CacheHelper,
) {

    fun findSummonerInfo(summonerName: String): SummonerDto? {
        return summonerService.findSummonerInfo(summonerName)?.apply {
            val cachedTime = cacheHelper.getCachedTime(summonerService.buildCachedTimeKey(summonerName))
            this.lastRefreshedAgo = cachedTime.diffFormattedStringFromNow()
        }
    }

    fun evictSummonerInfoAndMatchInfo(summonerName: String) {
        summonerService.findSummonerInfo(summonerName)?.let {
            summonerService.evictSummonerInfo(summonerName)
            matchService.evictMatchInfo(it.puuid)
        }
    }
}