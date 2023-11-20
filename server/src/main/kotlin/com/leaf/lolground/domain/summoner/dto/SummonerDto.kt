package com.leaf.lolground.domain.summoner.dto

data class SummonerDto(
    val id: String = "",
    val puuid: String = "",
    val summonerName: String = "Unknown",
    val soloTier: String = "UnRanked",
    val soloRank: String = "",
    val soloLeaguePoints: Int = 0,
    val soloWins: Int = 0,
    val soloLosses: Int = 0,
    val freeTier: String = "UnRanked",
    val freeRank: String = "",
    val freeLeaguePoints: Int = 0,
    val freeWins: Int = 0,
    val freeLosses: Int = 0,
    var lastRefreshedAgo: String = "",
) {
    val soloWinRate: String
        get() {
            val winRate: Double = this.soloWins.toDouble() / (this.soloWins + this.soloLosses).toDouble() * 100
            return when (winRate.isNaN()) {
                true -> "0"
                else -> String.format("%.2f", winRate)
            }
        }

    val freeWinRate: String
        get() {
            val winRate: Double = this.freeWins.toDouble() / (this.freeWins + this.freeLosses).toDouble() * 100
            return when (winRate.isNaN()) {
                true -> "0"
                else -> String.format("%.2f", winRate)
            }
        }

    fun isEmpty(): Boolean {
        return this.puuid.isEmpty()
    }
}