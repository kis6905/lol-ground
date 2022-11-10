package com.leaf.lolground.infrastructure.api.lol.league

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.leaf.lolground.infrastructure.api.lol.league.dto.League
import com.leaf.lolground.infrastructure.api.lol.summoner.dto.Summoner
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

private val logger = KotlinLogging.logger {}

@Repository
class LeagueApi(
    val httpClient: HttpClient,
) {
    companion object {
        const val findLeagueBySummonerUrl = "/lol/league/v4/entries/by-summoner/"
    }

    @Value("\${lol.api.endpoint.league}")
    lateinit var endpoint: String
    @Value("\${lol.api.token}")
    lateinit var apiToken: String

    @CircuitBreaker(name = "findLeagueBySummoner", fallbackMethod = "fallbackFindLeagueBySummoner")
    suspend fun findLeagueListBySummoner(summonerId: String): List<League> {
        logger.info("[API request] findLeagueBySummoner: summonerId: $summonerId")

        val request: HttpRequest = HttpRequest.newBuilder()
            .uri(URI.create("$endpoint$findLeagueBySummonerUrl$summonerId"))
            .GET()
            .header("X-Riot-Token", apiToken)
            .build()

        val response: HttpResponse<String> = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

        if (response.statusCode() != HttpStatus.OK.value()) {
            throw RuntimeException("[API error] summonerId: $summonerId, statusCode: ${response.statusCode()}, message: ${response.body()}")
        }

        return response.body()?.let {
            logger.info("[API request] findLeagueBySummoner: OK, summonerId: $summonerId")
            val mapper = jacksonObjectMapper()
            mapper.readValue(it, object: TypeReference<List<League>>() {})
        }?: throw RuntimeException("[API error] findLeagueBySummoner: body is null, summonerId: $summonerId")
    }

    fun fallbackFindLeagueBySummoner(e: Throwable): Summoner {
        logger.error("[API fallback] findLeagueBySummoner", e)
        return Summoner.empty()
    }
}