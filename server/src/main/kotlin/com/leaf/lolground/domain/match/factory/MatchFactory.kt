package com.leaf.lolground.domain.match.factory

import com.leaf.lolground.domain.match.dto.RecentMatch
import com.leaf.lolground.infrastructure.api.lol.match.dto.MatchInfo
import com.leaf.lolground.infrastructure.helper.diffDaysFromNow
import com.leaf.lolground.infrastructure.helper.diffHoursFromNow
import com.leaf.lolground.infrastructure.helper.diffMinutesFromNow
import com.leaf.lolground.infrastructure.helper.toLocalDateTime
import org.springframework.stereotype.Component

@Component
class MatchFactory {
    fun createRecentMatch(matchInfo: MatchInfo, puuid: String): RecentMatch {
        val gameStartTime = matchInfo.gameStartTime.toLocalDateTime()

        val diffMinutes = gameStartTime.diffMinutesFromNow()
        val diffHours = gameStartTime.diffHoursFromNow()
        val playedAgo = if (diffMinutes < 60) {
            "${diffMinutes}m"
        } else if (diffHours < 24) {
            "${diffHours}h"
        } else {
            "${gameStartTime.diffDaysFromNow()}d"
        }

        return RecentMatch(
            win = matchInfo.isWin(puuid),
            playedAgo = playedAgo,
        )
    }
}
