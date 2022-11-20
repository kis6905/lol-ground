package com.leaf.lolground.infrastructure.api.lol.summoner

import com.leaf.lolground.infrastructure.api.lol.BaseLoLApi
import com.leaf.lolground.infrastructure.api.lol.summoner.dto.Summoner
import com.leaf.lolground.infrastructure.helper.parseJson
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
class SummonerApi(
    val httpClient: HttpClient,
): BaseLoLApi() {
    companion object {
        const val findSummonerUrl = "/lol/summoner/v4/summoners/by-name/{summonerName}"
    }

    @Value("\${lol.api.endpoint.summoner}")
    lateinit var endpoint: String

    @Cacheable(value = ["summoner"], key = "#summonerName", unless = "#result == null")
    @CircuitBreaker(name = "findSummoner", fallbackMethod = "fallbackFindSummoner")
    fun findSummonerWithCache(summonerName: String): Summoner? {
        val uri = UriComponentsBuilder.fromHttpUrl(endpoint)
            .path(findSummonerUrl)
            .build(summonerName)

        logger.info("[API request] findSummoner: summonerName: $summonerName, uri: $uri")

        val request: HttpRequest = makeHttpRequestBuilder()
            .uri(uri)
            .GET()
            .header(API_TOKEN_HEADER_NAME, apiToken)
            .build()

        val response: HttpResponse<String> = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

        if (response.statusCode() != HttpStatus.OK.value()) {
            throw RuntimeException("[API error] findSummoner: summonerName: $summonerName, statusCode: ${response.statusCode()}, message: ${response.body()}")
        }

        return response.body()?.let {
            logger.info("[API request] findSummoner OK, summonerName: $summonerName")
            it.parseJson()
        }?: throw RuntimeException("[API error] findSummoner: body is null, summonerName: $summonerName")
    }

    fun fallbackFindSummoner(e: Throwable): Summoner? {
        logger.error("[API fallback] findSummoner: ", e)
        return null
    }

    @Scheduled(fixedRateString = (1000 * 60 * 60 * 24 * 3).toString()) // 3 days
    @CacheEvict(value = ["summoner"], allEntries = true)
    fun evictSummoner(): Unit {
        logger.info("Evict cache: summoner")
    }
}