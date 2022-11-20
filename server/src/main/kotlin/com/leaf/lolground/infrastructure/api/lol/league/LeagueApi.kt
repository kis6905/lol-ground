package com.leaf.lolground.infrastructure.api.lol.league

import com.leaf.lolground.infrastructure.api.lol.BaseLoLApi
import com.leaf.lolground.infrastructure.api.lol.league.dto.League
import com.leaf.lolground.infrastructure.api.lol.summoner.dto.Summoner
import com.leaf.lolground.infrastructure.helper.parseJsonList
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.util.UriComponentsBuilder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

private val logger = KotlinLogging.logger {}

@Repository
class LeagueApi(
    val httpClient: HttpClient,
): BaseLoLApi() {
    companion object {
        const val findLeagueBySummonerUrl = "/lol/league/v4/entries/by-summoner/{summonerId}"
    }

    @Value("\${lol.api.endpoint.league}")
    lateinit var endpoint: String

    @CircuitBreaker(name = "findLeagueBySummoner", fallbackMethod = "fallbackFindLeagueBySummoner")
    suspend fun findLeagueListBySummoner(summonerId: String): List<League> {
        val uri = UriComponentsBuilder.fromHttpUrl(endpoint)
            .path(findLeagueBySummonerUrl)
            .build(summonerId)

        logger.info("[API request] findLeagueBySummoner: summonerId: $summonerId, uri: $uri")

        val request: HttpRequest = makeHttpRequestBuilder()
            .uri(uri)
            .GET()
            .header(API_TOKEN_HEADER_NAME, apiToken)
            .build()

        val response: HttpResponse<String> = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

        if (response.statusCode() != HttpStatus.OK.value()) {
            throw RuntimeException("[API error] summonerId: $summonerId, statusCode: ${response.statusCode()}, message: ${response.body()}")
        }

        return response.body()?.let {
            logger.info("[API request] findLeagueBySummoner: OK, summonerId: $summonerId")
            it.parseJsonList()
        }?: throw RuntimeException("[API error] findLeagueBySummoner: body is null, summonerId: $summonerId")
    }

    fun fallbackFindLeagueBySummoner(e: Throwable): Summoner {
        logger.error("[API fallback] findLeagueBySummoner", e)
        return Summoner.empty()
    }
}