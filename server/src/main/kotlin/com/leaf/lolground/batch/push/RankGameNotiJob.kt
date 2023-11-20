package com.leaf.lolground.batch.push

import com.leaf.lolground.infrastructure.helper.logger
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class RankGameNotiJob(
    val rankGameNotiFacade: RankGameNotiFacade
) {

    @Scheduled(fixedRateString = (1000 * 60 * 5).toString()) // 5 min
    fun rankStartNotiTask() {
        logger.info("[rankStartNotiTask] start")

    }

}
