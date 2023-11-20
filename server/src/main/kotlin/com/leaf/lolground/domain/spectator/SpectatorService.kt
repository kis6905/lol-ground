package com.leaf.lolground.domain.spectator

import com.leaf.lolground.infrastructure.api.lol.spectator.SpectatorApi
import com.leaf.lolground.infrastructure.api.lol.spectator.dto.CurrentGameInfo
import com.leaf.lolground.infrastructure.helper.withBlockAndIOContext
import kotlinx.coroutines.async
import mu.KotlinLogging
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class SpectatorService(
    val spectatorApi: SpectatorApi,
) {

    @Cacheable(value = ["currentGame"], key = "#summonerId", unless = "#result == null")
    fun findCurrentGame(summonerId: String): CurrentGameInfo? = withBlockAndIOContext co@{
        val currentGameDef = async { spectatorApi.findCurrentGame(summonerId) }
        return@co currentGameDef.await()
    }

    @Scheduled(fixedRateString = (1000 * 60 * 5).toString()) // 5 min
    @CacheEvict(value = ["currentGame"], allEntries = true)
    fun evictCurrentGame() {
        logger.info("Evict cache: currentGame")
    }

}
