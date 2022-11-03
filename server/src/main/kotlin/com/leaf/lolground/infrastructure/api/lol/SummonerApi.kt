package com.leaf.lolground.infrastructure.api.lol

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.leaf.lolground.domain.summoner.dto.Summoner
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse


private val logger = KotlinLogging.logger {}

@Repository
class SummonerApi {

    @CircuitBreaker(name = "findSummoner", fallbackMethod = "fallbackFindSummoner")
    fun findSummoner(summonerName: String): Summoner {
        logger.info("[API REQUEST] findSummoner, summonerName: $summonerName")

        val client: HttpClient = HttpClient.newHttpClient()
        val request: HttpRequest = HttpRequest
            .newBuilder()
            .uri(URI.create("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/$summonerName"))
            .GET()
            .header("X-Riot-Token", "RGAPI-c644f611-f269-4f91-85e6-01d9626e9765")
            .build()

        val response: HttpResponse<String> = client.send(request, HttpResponse.BodyHandlers.ofString())

        if (response.statusCode() != HttpStatus.OK.value()) {
            throw RuntimeException("[API ERROR] summonerName: $summonerName, statusCode: ${response.statusCode()}, message: ${response.body()}")
        }

        return response.body()?.let {
            val mapper = jacksonObjectMapper()
            mapper.readValue(it, Summoner::class.java)
        }?: throw RuntimeException("[API ERROR] body is null, summonerName: $summonerName")
    }

    fun fallbackFindSummoner(e: Throwable): Summoner {
        logger.error("[API fallback] fallbackFindSummoner", e)
        return Summoner.empty()
    }
}