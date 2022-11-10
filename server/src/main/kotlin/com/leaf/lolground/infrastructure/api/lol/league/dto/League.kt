package com.leaf.lolground.infrastructure.api.lol.league.dto

import com.leaf.lolground.infrastructure.api.lol.league.constants.QueueType

data class League(
    val leagueId: String,
    val queueType: QueueType,
    val tier: String,
    val rank: String,
    val summonerId: String,
    val summonerName: String,
    val leaguePoints: Int = 0,
    val wins: Int = 0,
    val losses: Int = 0,
    val veteran: Boolean = false,
    val inactive: Boolean = false,
    val freshBlood: Boolean = false,
    val hotStreak: Boolean = false,
)

fun List<League>.findByQueueType(queueType: QueueType): League? =
    this.find { it.queueType == queueType }
