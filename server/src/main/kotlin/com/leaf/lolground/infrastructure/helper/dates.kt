package com.leaf.lolground.infrastructure.helper

import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

fun Date.toLocalDateTime(): LocalDateTime =
    this.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()

fun LocalDateTime.isToday(): Boolean {
    val midnight = getMidnightOfToday()
    return this.isAfter(midnight)
}

fun LocalDateTime.diffMinutesFromNow(): Long {
    val now = LocalDateTime.now()
    val duration = Duration.between(this, now)
    return duration.toMinutes()
}

fun LocalDateTime.diffHoursFromNow(): Long {
    val now = LocalDateTime.now()
    val duration = Duration.between(this, now)
    return duration.toHours()
}

fun LocalDateTime.diffDaysFromNow(): Long {
    val now = LocalDateTime.now()
    val duration = Duration.between(this, now)
    return duration.toDays()
}

fun getMidnightOfToday(): LocalDateTime {
    val now = LocalDateTime.now()
    return LocalDateTime.of(now.year, now.month, now.dayOfMonth, 0, 0, 0)
}
