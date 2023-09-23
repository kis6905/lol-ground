package com.leaf.lolground.domain.subscriber

import com.appmattus.kotlinfixture.kotlinFixture
import com.leaf.lolground.domain.subscriber.dto.SubscriberRegistrationDto
import com.leaf.lolground.domain.subscriber.factory.SubscriberFactory
import com.leaf.lolground.infrastructure.database.subscriber.entity.Subscriber
import com.leaf.lolground.infrastructure.database.subscriber.repository.SubscriberRepository
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class SubscriberServiceTest: BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    val subscriberRepository: SubscriberRepository = mockk()
    val subscriberFactory: SubscriberFactory = mockk()
    val sut = SubscriberService(subscriberRepository, subscriberFactory)

    val fixture = kotlinFixture()

    val appId = "1"
    val entity1 = fixture<Subscriber> {
        property(Subscriber::appId) { appId }
        property(Subscriber::summonerName) { "a" }
    }
    val entity2 = fixture<Subscriber> {
        property(Subscriber::appId) { appId }
        property(Subscriber::summonerName) { "b" }
    }
    val entity3 = fixture<Subscriber> {
        property(Subscriber::appId) { appId }
        property(Subscriber::summonerName) { "c" }
    }

    given("save - 삭제, 저장 둘 다 있는 경우") {
        val registrationDto = SubscriberRegistrationDto(
            appId = appId,
            summonerNameList = listOf("a", "b", "d", "e")
        )

        `when`("when") {
            every { subscriberRepository.findAllByAppId(appId) } answers { listOf(entity1, entity2, entity3) }
            every { subscriberFactory.createSubscriberEntity(any(), appId) } answers { fixture() }
            every { subscriberRepository.deleteAll(any()) } answers { Unit }
            every { subscriberRepository.saveAll(any<List<Subscriber>>()) } answers { listOf() }

            sut.save(registrationDto)

            then("then") {
                verify(exactly = 1) { subscriberRepository.deleteAll(any()) }
                verify(exactly = 1) { subscriberRepository.saveAll(any<List<Subscriber>>()) }
                verify(exactly = 2) { subscriberFactory.createSubscriberEntity(any(), appId) }
            }
        }
    }

    given("save - 저장만 있는 경우") {
        val registrationDto = SubscriberRegistrationDto(
            appId = appId,
            summonerNameList = listOf("a", "b", "c", "d", "e")
        )

        `when`("when") {
            every { subscriberRepository.findAllByAppId(appId) } answers { listOf(entity1, entity2, entity3) }
            every { subscriberFactory.createSubscriberEntity(any(), appId) } answers { fixture() }
            every { subscriberRepository.deleteAll(any()) } answers { Unit }
            every { subscriberRepository.saveAll(any<List<Subscriber>>()) } answers { listOf() }

            sut.save(registrationDto)

            then("then") {
                verify(exactly = 0) { subscriberRepository.deleteAll(any()) }
                verify(exactly = 1) { subscriberRepository.saveAll(any<List<Subscriber>>()) }
                verify(exactly = 2) { subscriberFactory.createSubscriberEntity(any(), appId) }
            }
        }
    }

    given("save - 삭제만 있는 경우") {
        val registrationDto = SubscriberRegistrationDto(
            appId = appId,
            summonerNameList = listOf("a")
        )

        `when`("when") {
            every { subscriberRepository.findAllByAppId(appId) } answers { listOf(entity1, entity2, entity3) }
            every { subscriberFactory.createSubscriberEntity(any(), appId) } answers { fixture() }
            every { subscriberRepository.deleteAll(any()) } answers { Unit }
            every { subscriberRepository.saveAll(any<List<Subscriber>>()) } answers { listOf() }

            sut.save(registrationDto)

            then("then") {
                verify(exactly = 1) { subscriberRepository.deleteAll(any()) }
                verify(exactly = 0) { subscriberRepository.saveAll(any<List<Subscriber>>()) }
                verify(exactly = 0) { subscriberFactory.createSubscriberEntity(any(), appId) }
            }
        }
    }

})
