package com.leaf.lolground.domain.app.dto

import java.time.LocalDateTime

data class AppDto(
    val appId: String,
    val fcmToken: String? = null,
    val lastLoginAt: LocalDateTime? = null,
)
