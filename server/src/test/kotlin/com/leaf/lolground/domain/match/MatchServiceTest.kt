package com.leaf.lolground.domain.match

import com.appmattus.kotlinfixture.kotlinFixture
import com.leaf.lolground.domain.match.factory.MatchFactory
import com.leaf.lolground.infrastructure.api.lol.match.MatchApi
import com.leaf.lolground.infrastructure.api.lol.match.constants.QueueId
import com.leaf.lolground.infrastructure.api.lol.match.dto.Match
import com.leaf.lolground.infrastructure.api.lol.match.dto.MatchInfo
import com.leaf.lolground.infrastructure.helper.isUtilDate
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDateTime

class MatchServiceTest: BehaviorSpec({
    val matchApi = mockk<MatchApi>()
    val matchFactory = mockk<MatchFactory>()
    val sut = MatchService(matchApi, matchFactory)

    val fixture = kotlinFixture()

    given("findMatchIdList") {
        val puuid = "puuid"
        val matchIdList = listOf("m1", "m2", "m3")
        val now = LocalDateTime.now()
        val past = LocalDateTime.now().minusDays(7)

        `when`("전부 이번주에 게임 한 경우") {
            every { matchApi.findMatchIdList(puuid) } answers { matchIdList }
            every { matchApi.findMatch(any()) } answers {
                Match(
                    matchInfo = MatchInfo(
                        gameStartTime = now.isUtilDate(),
                        queueId = QueueId.SOLO_RANK.id,
                        participants = fixture(),
                    )
                )
            }
            every { matchFactory.createRecentMatch(any(), any()) } answers { fixture() }

            val result = sut.findMatchInfo(puuid)

            then("then") {
                result.playedGameCountOfThisWeek shouldBe 3
            }
        }

        `when`("이번주에 게임 안 한 경우") {
            every { matchApi.findMatchIdList(puuid) } answers { matchIdList }
            every { matchApi.findMatch(any()) } answers {
                Match(
                    matchInfo = MatchInfo(
                        gameStartTime = past.isUtilDate(),
                        queueId = QueueId.SOLO_RANK.id,
                        participants = fixture(),
                    )
                )
            }

            val result = sut.findMatchInfo(puuid)

            then("then") {
                result.playedGameCountOfThisWeek shouldBe 0
            }
        }
    }

})