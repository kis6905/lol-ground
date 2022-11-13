package com.leaf.lolground.infrastructure.api.lol.match.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
data class MatchInfo(
    @JsonProperty("gameStartTimestamp")
    val gameStartTime: Date, // TODO: LocalDateTime 방법 찾아보기
    val queueId: Long, // 420 솔랭, 430 일반, 440 자유랭
    val participants: List<MatchParticipant>,
) {
}