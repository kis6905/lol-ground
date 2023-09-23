package com.leaf.lolground.domain.subscriber.factory

import com.leaf.lolground.domain.subscriber.dto.SubscriberDto
import com.leaf.lolground.infrastructure.database.subscriber.entity.Subscriber
import org.springframework.stereotype.Component

@Component
class SubscriberFactory {
    fun createSubscriberDto(entity: Subscriber): SubscriberDto {
        return SubscriberDto(
            subscriberId = entity.subscriberId,
            summonerName = entity.summonerName,
            appId = entity.appId,
        )
    }

    fun createSubscriberEntity(summonerName: String, appId: String): Subscriber {
        return Subscriber(
            summonerName = summonerName,
            appId = appId,
            _createdBy = appId,
            _modifiedBy = appId,
        )
    }

    fun createSubscriberEntity(dto: SubscriberDto): Subscriber {
        val appId = dto.appId ?: ""
        return Subscriber(
            summonerName = dto.summonerName,
            appId = appId,
            _createdBy = appId,
            _modifiedBy = appId,
        )
    }
}
