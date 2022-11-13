package com.leaf.lolground.domain.summoner.dto

data class SummonerDto(
    val puuid: String,
    val summonerName: String,
    val soloTier: String,
    val soloRank: String,
    val soloLeaguePoints: Int = 0,
    val soloWins: Int = 0,
    val soloLosses: Int = 0,
    val freeTier: String,
    val freeRank: String,
    val freeLeaguePoints: Int = 0,
    val freeWins: Int = 0,
    val freeLosses: Int = 0,
) {
    var soloWinRate: String = "0"
        get() {
            val winRate: Double = this.soloWins.toDouble() / (this.soloWins + this.soloLosses).toDouble() * 100
            return String.format("%.2f", winRate)
        }

    var freeWinRate: String = "0"
        get() {
            val winRate: Double = this.freeWins.toDouble() / (this.freeWins + this.freeLosses).toDouble() * 100
            return String.format("%.2f", winRate)
        }
}