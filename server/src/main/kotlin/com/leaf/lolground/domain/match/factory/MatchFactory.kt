package com.leaf.lolground.domain.match.factory

import com.leaf.lolground.domain.match.dto.RecentMatch
import com.leaf.lolground.infrastructure.api.lol.match.dto.MatchInfo
import com.leaf.lolground.infrastructure.helper.diffFormattedStringFromNow
import com.leaf.lolground.infrastructure.helper.toLocalDateTime
import org.springframework.stereotype.Component

@Component
class MatchFactory {
    fun createRecentMatch(matchInfo: MatchInfo, puuid: String): RecentMatch {
        val gameStartTime = matchInfo.gameStartTime.toLocalDateTime()

        return RecentMatch(
            win = matchInfo.isWin(puuid),
            playedAgo = gameStartTime.diffFormattedStringFromNow(),
        )
    }
}
