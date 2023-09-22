package com.leaf.lolground.domain.app

import com.appmattus.kotlinfixture.kotlinFixture
import com.leaf.lolground.domain.app.dto.AppDto
import com.leaf.lolground.domain.app.factory.AppFactory
import com.leaf.lolground.infrastructure.database.app.entity.App
import com.leaf.lolground.infrastructure.database.app.repository.AppRepository
import com.leaf.lolground.infrastructure.exceptions.NotFoundException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.dao.DuplicateKeyException
import org.springframework.data.repository.findByIdOrNull

class AppServiceTest: BehaviorSpec({
    val appRepository = mockk<AppRepository>()
    val appFactory = mockk<AppFactory>()
    val sut = AppService(appRepository, appFactory)

    val fixture = kotlinFixture()

    given("save") {
        val appId = "123"
        val entity = fixture<App>()
        val dto = AppDto(
            appId = appId,
            fcmToken = "abc"
        )

        `when`("성공 case 1 - DB에 데이터가 없을때") {
            every { appRepository.findByIdOrNull(appId) } answers { null }
            every { appFactory.createAppEntity(any(), any()) } answers { entity }
            every { appRepository.save(any()) } answers { entity }
            every { appFactory.createAppDto(any()) } answers { dto }

            sut.save(dto, "127.0.0.1")

            then("then") {
                verify(exactly = 1) { appFactory.createAppEntity(any(), any()) }
                verify(exactly = 1) { appRepository.save(any()) }
                verify(exactly = 1) { appFactory.createAppDto(any()) }
            }
        }

        `when`("실패 case 1 - DB에 데이터가 있을때") {
            every { appRepository.findByIdOrNull(appId) } answers { entity }

            shouldThrow<DuplicateKeyException> {
                sut.save(dto, "127.0.0.1")
            }.apply {
                message shouldBe "Duplicated appId! [appId: $appId]"
            }
        }
    }

    given("updateFcmToken") {
        val appId = "123"
        val entity = fixture<App>()
        val dto = AppDto(
            appId = appId,
            fcmToken = "abc"
        )

        `when`("성공 case 1 - DB에 데이터가 있을때") {
            every { appRepository.findByIdOrNull(appId) } answers { entity }
            every { appFactory.createAppDto(any()) } answers { dto }

            sut.updateFcmToken(dto, "127.0.0.1")

            then("then") {
                verify(exactly = 1) { appFactory.createAppDto(any()) }
            }
        }

        `when`("실패 case 1 - DB에 데이터가 없을때") {
            every { appRepository.findByIdOrNull(appId) } answers { null }

            shouldThrow<NotFoundException> {
                sut.updateFcmToken(dto, "127.0.0.1")
            }.apply {
                message shouldBe "Not Found app! [appId: $appId]"
            }
        }
    }

})
