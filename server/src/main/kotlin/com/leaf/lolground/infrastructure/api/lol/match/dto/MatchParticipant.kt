package com.leaf.lolground.infrastructure.api.lol.match.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class MatchParticipant(
    val summonerId: String,
    val puuid: String,
    val summonerName: String,
    val teamId: Int,
    val win: Boolean,
)