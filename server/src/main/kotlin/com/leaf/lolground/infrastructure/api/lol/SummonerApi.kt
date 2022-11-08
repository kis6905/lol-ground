package com.leaf.lolground.infrastructure.api.lol

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.leaf.lolground.domain.summoner.dto.Summoner
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
class SummonerApi(
    val httpClient: HttpClient,
) {
    companion object {
        const val findSummonerUrl = "/lol/summoner/v4/summoners/by-name/"
    }

    @Value("\${lol.api.endpoint.summoner}")
    lateinit var endpoint: String
    @Value("\${lol.api.token}")
    lateinit var apiToken: String

    @CircuitBreaker(name = "findSummoner", fallbackMethod = "fallbackFindSummoner")
    suspend fun findSummoner(summonerName: String): Summoner {
        logger.info("[API REQUEST] findSummoner, summonerName: $summonerName")

        val request: HttpRequest = HttpRequest
            .newBuilder()
            .uri(URI.create("$endpoint$findSummonerUrl$summonerName"))
            .GET()
            .header("X-Riot-Token", apiToken)
            .build()

        val response: HttpResponse<String> = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

        if (response.statusCode() != HttpStatus.OK.value()) {
            throw RuntimeException("[API ERROR] summonerName: $summonerName, statusCode: ${response.statusCode()}, message: ${response.body()}")
        }

        return response.body()?.let {
            logger.info("[API REQUEST] findSummoner OK, summonerName: $summonerName")
            val mapper = jacksonObjectMapper()
            mapper.readValue(it, Summoner::class.java)
        }?: throw RuntimeException("[API ERROR] body is null, summonerName: $summonerName")
    }

    fun fallbackFindSummoner(e: Throwable): Summoner {
        logger.error("[API fallback] fallbackFindSummoner", e)
        return Summoner.empty()
    }
}