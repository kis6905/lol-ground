package com.leaf.lolground.infrastructure.helper

import java.time.*
import java.util.*

fun Date.toLocalDateTime(): LocalDateTime =
    this.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()

fun LocalDateTime.isUtilDate(): Date {
    return Date.from(this.atZone(ZoneId.systemDefault()).toInstant())
}

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

fun LocalDateTime.getMondayOfCurrentWeek(): LocalDateTime {
    return this.with(DayOfWeek.MONDAY)
}

fun LocalDateTime?.diffFormattedStringFromNow(): String {
    if (this == null) {
        return "0m"
    }
    val diffMinutes = this.diffMinutesFromNow()
    val diffHours = this.diffHoursFromNow()
    return if (diffMinutes < 60) {
        "${diffMinutes}m"
    } else if (diffHours < 24) {
        "${diffHours}h"
    } else {
        "${this.diffDaysFromNow()}d"
    }
}
