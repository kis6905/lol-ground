package com.leaf.lolground.domain.match.factory

import com.appmattus.kotlinfixture.kotlinFixture
import com.leaf.lolground.domain.match.factory.MatchFactory
import com.leaf.lolground.infrastructure.api.lol.league.dto.League
import com.leaf.lolground.infrastructure.api.lol.match.MatchApi
import com.leaf.lolground.infrastructure.api.lol.match.constants.QueueId
import com.leaf.lolground.infrastructure.api.lol.match.dto.Match
import com.leaf.lolground.infrastructure.api.lol.match.dto.MatchInfo
import com.leaf.lolground.infrastructure.helper.isUtilDate
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.time.LocalDateTime

class MatchFactoryTest: BehaviorSpec({
    val sut = MatchFactory()

    val fixture = kotlinFixture()

    val puuid = "puuid"
    val now = LocalDateTime.now()
    val ago20Minutes = now.minusMinutes(20)
    val ago1Hour = now.minusHours(1)
    val ago3Hours = now.minusHours(3)
    val ago1Day = now.minusDays(1)
    val ago2Days = now.minusDays(2)

    given("playedAgo - 20분 전") {
        val matchInfo = fixture<MatchInfo> { property(MatchInfo::gameStartTime) { ago20Minutes.isUtilDate() } }
        `when`("when") {
            val result = sut.createRecentMatch(matchInfo, puuid)
            then("then") {
                result.playedAgo shouldBe "20 분"
            }
        }
    }

    given("playedAgo - 1시간 전") {
        val matchInfo = fixture<MatchInfo> { property(MatchInfo::gameStartTime) { ago1Hour.isUtilDate() } }
        `when`("when") {
            val result = sut.createRecentMatch(matchInfo, puuid)
            then("then") {
                result.playedAgo shouldBe "1 시간"
            }
        }
    }

    given("playedAgo - 3시간 전") {
        val matchInfo = fixture<MatchInfo> { property(MatchInfo::gameStartTime) { ago3Hours.isUtilDate() } }
        `when`("when") {
            val result = sut.createRecentMatch(matchInfo, puuid)
            then("then") {
                result.playedAgo shouldBe "3 시간"
            }
        }
    }

    given("playedAgo - 1일 전") {
        val matchInfo = fixture<MatchInfo> { property(MatchInfo::gameStartTime) { ago1Day.isUtilDate() } }
        `when`("when") {
            val result = sut.createRecentMatch(matchInfo, puuid)
            then("then") {
                result.playedAgo shouldBe "1 일"
            }
        }
    }

    given("playedAgo - 2일 전") {
        val matchInfo = fixture<MatchInfo> { property(MatchInfo::gameStartTime) { ago2Days.isUtilDate() } }
        `when`("when") {
            val result = sut.createRecentMatch(matchInfo, puuid)
            then("then") {
                result.playedAgo shouldBe "2 일"
            }
        }
    }

})