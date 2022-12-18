package com.leaf.lolground.domain.summoner

import com.appmattus.kotlinfixture.kotlinFixture
import com.leaf.lolground.domain.summoner.dto.SummonerDto
import com.leaf.lolground.domain.summoner.factory.SummonerFactory
import com.leaf.lolground.infrastructure.api.lol.league.LeagueApi
import com.leaf.lolground.infrastructure.api.lol.league.constants.QueueType
import com.leaf.lolground.infrastructure.api.lol.league.dto.League
import com.leaf.lolground.infrastructure.api.lol.summoner.SummonerApi
import com.leaf.lolground.infrastructure.api.lol.summoner.dto.Summoner
import com.leaf.lolground.infrastructure.cache.CacheHelper
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class SummonerServiceTest: BehaviorSpec({
    val summonerApi = mockk<SummonerApi>()
    val leagueApi = mockk<LeagueApi>()
    val summonerFactory = mockk<SummonerFactory>()
    val cacheHelper = mockk<CacheHelper>()
    val sut = SummonerService(
        summonerApi = summonerApi,
        leagueApi = leagueApi,
        summonerFactory = summonerFactory,
        cacheHelper = cacheHelper,
    )

    val fixture = kotlinFixture()

    given("findSummonerInfo - 정상") {
        val summonerName = "1leaf"
        val summonerId = fixture<String>()
        val puuid = "puuid"
        val summoner: Summoner = fixture {
            property(Summoner::id) { summonerId }
            property(Summoner::puuid) { puuid }
            property(Summoner::name) { summonerName }
        }

        val leagueList = listOf(
            fixture<League> {
                property(League::queueType) { QueueType.RANKED_SOLO_5x5 }
                property(League::leaguePoints) { 50 }
            }
        )

        every { summonerApi.findSummoner(summonerName) } answers { summoner }
        every { leagueApi.findLeagueListBySummoner(summonerId) } answers { leagueList }
        every { cacheHelper.setCachedTime(any()) } answers { }
        every { summonerFactory.createSummonerDto(any(), any()) } answers { SummonerDto(puuid = puuid) }

        `when`("when") {
            val result = sut.findSummonerInfo(summonerName)

            then("then") {
                verify(exactly = 1) { summonerApi.findSummoner(summonerName) }
                coVerify(exactly = 1) { leagueApi.findLeagueListBySummoner(summonerId) }
                result!!.puuid shouldBe "puuid"
            }
        }
    }

    given("findSummonerInfo - Summoner 가 없는 경우") {
        val summonerName = "1leaf"
        every { summonerApi.findSummoner(summonerName) } answers { null }

        `when`("when") {
            val result = sut.findSummonerInfo(summonerName)

            then("then") {
                verify(exactly = 1) { summonerApi.findSummoner(any()) }
                coVerify(exactly = 0) { leagueApi.findLeagueListBySummoner(any()) }
                result shouldBe null
            }
        }
    }

})