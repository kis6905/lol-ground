package com.leaf.lolground.infrastructure.database.subscriber.repository

import com.leaf.lolground.infrastructure.database.subscriber.entity.Subscriber
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SubscriberRepository: JpaRepository<Subscriber, String> {
    fun findAllBySummonerName(summonerName: String): List<Subscriber>
    fun findAllByAppId(appId: String): List<Subscriber>
}
