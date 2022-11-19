package com.leaf.lolground.infrastructure.api.lol.match.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.leaf.lolground.infrastructure.helper.getMidnightOfToday
import com.leaf.lolground.infrastructure.helper.getMondayOfCurrentWeek
import com.leaf.lolground.infrastructure.helper.toLocalDateTime
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
data class MatchInfo(
    @JsonProperty("gameStartTimestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    val gameStartTime: Date, // TODO: LocalDateTime 방법 찾아보기
    val queueId: Int, // 420 솔랭, 430 일반, 440 자유랭
    val participants: List<MatchParticipant>,
) {
    fun isWin(puuid: String): Boolean {
        return participants.find { it.puuid == puuid }?.win ?: false
    }

    fun isThisWeek(): Boolean {
        val nowMidnight = getMidnightOfToday()
        val thisMonday = nowMidnight.getMondayOfCurrentWeek()
        return gameStartTime.toLocalDateTime().isAfter(thisMonday)
    }
}