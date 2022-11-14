package com.leaf.lolground.domain.match.dto

data class MatchDto(
    val playedGameCountOfThisWeek: Int = 0,
    val recentMatches: List<RecentMatch> = emptyList()
)
