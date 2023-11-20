package com.leaf.lolground.batch.push

import com.leaf.lolground.domain.app.AppService
import com.leaf.lolground.domain.spectator.SpectatorService
import com.leaf.lolground.domain.subscriber.SubscriberService
import com.leaf.lolground.domain.summoner.SummonerService
import com.leaf.lolground.infrastructure.api.lol.match.constants.QueueId
import org.springframework.stereotype.Service

@Service
class RankGameNotiFacade(
    val spectatorService: SpectatorService,
    val appService: AppService,
    val subscriberService: SubscriberService,
    val summonerService: SummonerService,
) {
    fun sendPushRankGameStart() {
        val appList = appService.findActiveAppList()

        appList.forEach { app ->
            val subscriberList = subscriberService.findSubscriberList(app.appId)

            val filteredList = subscriberList
                .filterNot { it.summonerName == null || it.summonerName.isEmpty() }
                .map { summonerService.findSummonerInfo(it.summonerName!!) }
                .filter {
                    Thread.sleep(250) // Riot API Limit 으로 인해 sleep
                    val currentGame = spectatorService.findCurrentGame(it!!.id)
                    val queueId = currentGame?.gameQueueConfigId ?: -1
                    QueueId.isSoloRank(queueId)
                }
                .forEach {
                    // TODO
                }
        }

    }
}
