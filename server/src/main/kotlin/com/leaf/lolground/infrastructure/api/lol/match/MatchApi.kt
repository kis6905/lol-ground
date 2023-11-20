package com.leaf.lolground.infrastructure.api.lol.match

import com.leaf.lolground.infrastructure.api.lol.BaseLoLApi
import com.leaf.lolground.infrastructure.api.lol.match.constants.QueueId
import com.leaf.lolground.infrastructure.api.lol.match.dto.Match
import com.leaf.lolground.infrastructure.helper.parseJson
import com.leaf.lolground.infrastructure.helper.parseJsonList
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.HttpStatus
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Repository
import org.springframework.web.util.UriComponentsBuilder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse


private val logger = KotlinLogging.logger {}

@Repository
class MatchApi(
    val httpClient: HttpClient,
): BaseLoLApi() {
    companion object {
        const val findMatchIdListUrl = "/lol/match/v5/matches/by-puuid/{puuid}/ids"
        const val findMatchUrl = "/lol/match/v5/matches/{matchId}"
    }

    @Value("\${lol.api.endpoint.match}")
    lateinit var endpoint: String

    @CircuitBreaker(name = "findMatchIdList", fallbackMethod = "fallbackFindMatchIdList")
    fun findMatchIdList(puuid: String, start: Int = 0, count: Int = 5): List<String> {
        val uri = UriComponentsBuilder.fromHttpUrl(endpoint)
            .path(findMatchIdListUrl)
            .queryParam("start", start)
            .queryParam("count", count)
            .queryParam("queue", QueueId.SOLO_RANK.id)
            .build(puuid)

        logger.info("[API request] findMatchIdList: puuid: $puuid, uri: $uri")

        val request: HttpRequest = makeHttpRequestBuilder()
            .uri(uri)
            .GET()
            .header(API_TOKEN_HEADER_NAME, apiToken)
            .build()

        val response: HttpResponse<String> = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

        if (response.statusCode() != HttpStatus.OK.value()) {
            throw RuntimeException("[API error] findMatchIdList: puuid: $puuid, statusCode: ${response.statusCode()}, message: ${response.body()}")
        }

        return response.body()?.let {
            logger.info("[API request] findMatchIdList OK, puuid: $puuid")
            it.parseJsonList()
        }?: throw RuntimeException("[API error] findMatchIdList: body is null, puuid: $puuid")
    }

    fun fallbackFindMatchIdList(e: Throwable): List<String> {
        logger.error("[API fallback] findMatchIdList: ", e)
        return emptyList()
    }

    @Cacheable(value = ["match"], key = "#matchId", unless = "#result == null")
    @CircuitBreaker(name = "findMatch", fallbackMethod = "fallbackFindMatch")
    fun findMatch(matchId: String): Match? {
        val uri = UriComponentsBuilder.fromHttpUrl(endpoint)
            .path(findMatchUrl)
            .build(matchId)

        logger.info("[API request] findMatch: matchId: $matchId, uri: $uri")

        val request: HttpRequest = makeHttpRequestBuilder()
            .uri(uri)
            .GET()
            .header(API_TOKEN_HEADER_NAME, apiToken)
            .build()

        val response: HttpResponse<String> = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

        if (response.statusCode() != HttpStatus.OK.value()) {
            throw RuntimeException("[API error] findMatch: matchId: $matchId, statusCode: ${response.statusCode()}, message: ${response.body()}")
        }

        return response.body()?.let {
            logger.info("[API request] findMatch OK, matchId: $matchId")
            it.parseJson()
        } ?: throw RuntimeException("[API error] findMatch: body is null, matchId: $matchId")
    }

    fun fallbackFindMatch(e: Throwable): Match? {
        logger.error("[API fallback] findMatch: ", e)
        return null
    }

    @Scheduled(fixedRateString = (1000 * 60 * 60 * 24 * 10).toString()) // 10days
    @CacheEvict(value = ["match"], allEntries = true)
    fun evictMatch() {
        logger.info("Evict cache: match")
    }
}
