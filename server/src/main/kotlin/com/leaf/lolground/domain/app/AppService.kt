package com.leaf.lolground.domain.app

import com.leaf.lolground.domain.app.dto.AppDto
import com.leaf.lolground.domain.app.factory.AppFactory
import com.leaf.lolground.infrastructure.database.app.repository.AppRepository
import com.leaf.lolground.infrastructure.exceptions.NotFoundException
import org.springframework.dao.DuplicateKeyException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class AppService(
    val appRepository: AppRepository,
    val appFactory: AppFactory,
) {

    fun save(appDto: AppDto, ip: String?): AppDto? {
        val app = appRepository.findByIdOrNull(appDto.appId)
        app?.apply {
            throw DuplicateKeyException("Duplicated appId! [appId: ${appDto.appId}]")
        }

        val entity = appFactory.createAppEntity(appDto, ip)
        val saved = appRepository.save(entity)
        return appFactory.createAppDto(saved)
    }

    @Transactional(readOnly = false)
    fun updateFcmToken(appDto: AppDto, ip: String?): AppDto {
        val app = appRepository.findByIdOrNull(appDto.appId)
            ?: throw NotFoundException("Not Found app! [appId: ${appDto.appId}]")
        app.fcmToken = appDto.fcmToken
        app.lastLoginAt = LocalDateTime.now()
        app.ip = ip
        app.modifiedBy = appDto.appId
        return appFactory.createAppDto(app)
    }
}
