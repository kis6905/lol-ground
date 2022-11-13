package com.leaf.lolground.infrastructure.api.lol.summoner

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.leaf.lolground.infrastructure.api.lol.match.MatchApi
import com.leaf.lolground.infrastructure.api.lol.summoner.dto.Summoner
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.HttpStatus
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Repository
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

private val logger = KotlinLogging.logger {}

@Repository
class SummonerApi(
    val httpClient: HttpClient,
) {
    companion object {
        const val findSummonerUrl = "/lol/summoner/v4/summoners/by-name/{summonerName}"
    }

    @Value("\${lol.api.endpoint.summoner}")
    lateinit var endpoint: String
    @Value("\${lol.api.token}")
    lateinit var apiToken: String

    @Cacheable(value = ["summoner"], key = "#summonerName")
    @CircuitBreaker(name = "findSummoner", fallbackMethod = "fallbackFindSummoner")
    fun findSummonerWithCache(summonerName: String): Summoner {
        val uri = UriComponentsBuilder.fromHttpUrl(endpoint)
            .path(findSummonerUrl)
            .build(summonerName)

        logger.info("[API request] findSummoner: summonerName: $summonerName, uri: $uri")

        val request: HttpRequest = HttpRequest.newBuilder()
            .uri(uri)
            .GET()
            .header("X-Riot-Token", apiToken)
            .build()

        val response: HttpResponse<String> = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

        if (response.statusCode() != HttpStatus.OK.value()) {
            throw RuntimeException("[API error] findSummoner: summonerName: $summonerName, statusCode: ${response.statusCode()}, message: ${response.body()}")
        }

        return response.body()?.let {
            logger.info("[API request] findSummoner OK, summonerName: $summonerName")
            val mapper = jacksonObjectMapper()
            mapper.readValue(it, Summoner::class.java)
        }?: throw RuntimeException("[API error] findSummoner: body is null, summonerName: $summonerName")
    }

    fun fallbackFindSummoner(e: Throwable): Summoner {
        logger.error("[API fallback] findSummoner: ", e)
        return Summoner.empty()
    }

    @Scheduled(fixedRateString = (1000 * 60 * 60).toString())
    @CacheEvict(value = ["summoner"], allEntries = true)
    fun evictSummoner(): Unit {
        logger.info("Evict cache: summoner")
    }
}