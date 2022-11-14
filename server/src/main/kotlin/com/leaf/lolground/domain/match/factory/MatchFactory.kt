package com.leaf.lolground.domain.match.factory

import com.leaf.lolground.domain.match.dto.RecentMatch
import com.leaf.lolground.infrastructure.api.lol.match.dto.Match
import com.leaf.lolground.infrastructure.api.lol.match.dto.MatchInfo
import com.leaf.lolground.infrastructure.helper.*
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class MatchFactory {
    fun createRecentMatch(matchInfo: MatchInfo, puuid: String): RecentMatch {
        val gameStartTime = matchInfo.gameStartTime.toLocalDateTime()

        println("gameStartTime: ${gameStartTime.toString()}")
        val diffMinutes = gameStartTime.diffMinutesFromNow()
        val diffHours = gameStartTime.diffHoursFromNow()
        val playedAgo = if (diffMinutes < 60) {
            "$diffMinutes 분"
        } else if (diffHours < 24) {
            "$diffHours 시간"
        } else {
            "${gameStartTime.diffDaysFromNow()} 일"
        }

        return RecentMatch(
            win = matchInfo.isWin(puuid),
            playedAgo = playedAgo,
        )
    }
}
