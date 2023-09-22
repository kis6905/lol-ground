package com.leaf.lolground.domain.app.factory

import com.leaf.lolground.domain.app.dto.AppDto
import com.leaf.lolground.infrastructure.database.app.entity.App
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class AppFactory {
    fun createAppDto(entity: App): AppDto {
        return AppDto(
            appId = entity.appId,
            fcmToken = entity.fcmToken,
            lastLoginAt = entity.lastLoginAt,
        )
    }

    fun createAppEntity(dto: AppDto, ip: String?): App {
        return App(
            appId = dto.appId,
            fcmToken = dto.fcmToken,
            ip = ip,
            lastLoginAt = LocalDateTime.now(),
            _createdBy = dto.appId,
            _modifiedBy = dto.appId,
        )
    }
}
