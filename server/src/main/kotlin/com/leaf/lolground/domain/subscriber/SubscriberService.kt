package com.leaf.lolground.domain.subscriber

import com.leaf.lolground.domain.subscriber.dto.SubscriberDto
import com.leaf.lolground.domain.subscriber.dto.SubscriberRegistrationDto
import com.leaf.lolground.domain.subscriber.factory.SubscriberFactory
import com.leaf.lolground.infrastructure.database.subscriber.entity.Subscriber
import com.leaf.lolground.infrastructure.database.subscriber.repository.SubscriberRepository
import com.leaf.lolground.infrastructure.helper.logger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SubscriberService(
    val subscriberRepository: SubscriberRepository,
    val subscriberFactory: SubscriberFactory,
) {

    @Transactional(readOnly = false)
    fun save(subscriberRegistrationDto: SubscriberRegistrationDto) {
        val appId = subscriberRegistrationDto.appId

        val dbSubscriberList = subscriberRepository.findAllByAppId(appId)
        val dbSubscriberMap = dbSubscriberList.associateBy { it.summonerName!! }
        val summonerNameMap = subscriberRegistrationDto.summonerNameList.associateBy { it }

        val shouldRemoveList = extractShouldRemove(dbSubscriberList, summonerNameMap)
        val shouldSaveList = extractShouldSave(dbSubscriberMap, subscriberRegistrationDto.summonerNameList, appId)

        logger.info("[Subscriber] ")

        if (shouldRemoveList.isNotEmpty()) {
            subscriberRepository.deleteAll(shouldRemoveList)
        }
        if (shouldSaveList.isNotEmpty()) {
            subscriberRepository.saveAll(shouldSaveList)
        }
    }

    private fun extractShouldRemove(dbList: List<Subscriber>, summonerNameMap: Map<String, String>): List<Subscriber> {
        return dbList.filter { summonerNameMap[it.summonerName] == null }
    }

    private fun extractShouldSave(dbMap: Map<String, Subscriber>, summonerNameList: List<String>, appId: String): List<Subscriber> {
        return summonerNameList
            .filter { dbMap[it] == null }
            .map { subscriberFactory.createSubscriberEntity(it, appId) }
    }

    fun findSubscriberList(appId: String): List<SubscriberDto> {
        return subscriberRepository.findAllByAppId(appId)
            .map { subscriberFactory.createSubscriberDto(it) }
    }

}
