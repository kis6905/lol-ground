package com.leaf.lolground.infrastructure.api.lol.match

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.leaf.lolground.infrastructure.api.lol.match.constants.QueueId
import com.leaf.lolground.infrastructure.api.lol.match.dto.Match
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
) {
    companion object {
        const val findMatchIdListUrl = "/lol/match/v5/matches/by-puuid/{puuid}/ids"
        const val findMatchUrl = "/lol/match/v5/matches/{matchId}"
    }

    @Value("\${lol.api.endpoint.match}")
    lateinit var endpoint: String
    @Value("\${lol.api.token}")
    lateinit var apiToken: String

    @CircuitBreaker(name = "findMatchIdList", fallbackMethod = "fallbackFindMatchIdList")
    suspend fun findMatchIdList(puuid: String): List<String> {
        val uri = UriComponentsBuilder.fromHttpUrl(endpoint)
            .path(findMatchIdListUrl)
            .queryParam("start", 0)
            .queryParam("count", 5)
            .queryParam("queue", QueueId.SOLO_RANK.id)
            .build(puuid)

        logger.info("[API request] findMatchIdList: puuid: $puuid, uri: $uri")

        val request: HttpRequest = HttpRequest.newBuilder()
            .uri(uri)
            .GET()
            .header("X-Riot-Token", apiToken)
            .build()

        val response: HttpResponse<String> = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

        if (response.statusCode() != HttpStatus.OK.value()) {
            throw RuntimeException("[API error] findMatchIdList: puuid: $puuid, statusCode: ${response.statusCode()}, message: ${response.body()}")
        }

        return response.body()?.let {
            logger.info("[API request] findMatchIdList OK, puuid: $puuid")
            val mapper = jacksonObjectMapper()
            mapper.readValue(it, object: TypeReference<List<String>>() {})
        }?: throw RuntimeException("[API error] findMatchIdList: body is null, puuid: $puuid")
    }

    fun fallbackFindMatchIdList(e: Throwable): List<String> {
        logger.error("[API fallback] findMatchIdList: ", e)
        return emptyList()
    }

    @Cacheable(value = ["match"], key = "#matchId", unless = "#result == null")
    @CircuitBreaker(name = "findMatch", fallbackMethod = "fallbackFindMatch")
    fun findMatchWithCache(matchId: String): Match? {
        val uri = UriComponentsBuilder.fromHttpUrl(endpoint)
            .path(findMatchUrl)
            .build(matchId)

        logger.info("[API request] findMatch: matchId: $matchId, uri: $uri")

        val request: HttpRequest = HttpRequest.newBuilder()
            .uri(uri)
            .GET()
            .header("X-Riot-Token", apiToken)
            .build()

        val response: HttpResponse<String> = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

        if (response.statusCode() != HttpStatus.OK.value()) {
            throw RuntimeException("[API error] findMatch: matchId: $matchId, statusCode: ${response.statusCode()}, message: ${response.body()}")
        }

        return response.body()?.let {
            logger.info("[API request] findMatch OK, matchId: $matchId")
            val mapper = jacksonObjectMapper()
            mapper.readValue(it, Match::class.java)
        }?: throw RuntimeException("[API error] findMatch: body is null, matchId: $matchId")
    }

    fun fallbackFindMatch(e: Throwable): Match? {
        logger.error("[API fallback] findMatch: ", e)
        return null
    }

    @Scheduled(fixedRateString = (1000 * 60 * 60 * 24 * 3).toString())
    @CacheEvict(value = ["match"], allEntries = true)
    fun evictMatch(): Unit {
        logger.info("Evict cache: match")
    }
}