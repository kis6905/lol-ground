package com.leaf.lolground.infrastructure.api.lol.spectator

import com.leaf.lolground.infrastructure.api.lol.BaseLoLApi
import com.leaf.lolground.infrastructure.api.lol.spectator.dto.CurrentGameInfo
import com.leaf.lolground.infrastructure.helper.parseJson
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
class SpectatorApi(
    val httpClient: HttpClient,
): BaseLoLApi() {
    companion object {
        const val findCurrentGameUrl = "/lol/spectator/v4/active-games/by-summoner/{summonerId}"
    }

    @Value("\${lol.api.endpoint.spectator}")
    lateinit var endpoint: String

    @CircuitBreaker(name = "findCurrentGame", fallbackMethod = "fallbackFindCurrentGame")
    fun findCurrentGame(summonerId: String): CurrentGameInfo? {
        val uri = UriComponentsBuilder.fromHttpUrl(endpoint)
            .path(findCurrentGameUrl)
            .build(summonerId)

        logger.info("[API request] findCurrentGame: summonerId: $summonerId, uri: $uri")

        val request: HttpRequest = makeHttpRequestBuilder()
            .uri(uri)
            .GET()
            .header(API_TOKEN_HEADER_NAME, apiToken)
            .build()

        val response: HttpResponse<String> = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        val statusCode =  response.statusCode()
        
        if (HttpStatus.NOT_FOUND.value() == statusCode) {
            logger.info("[API request] findCurrentGame: not found current game, summonerId: $summonerId")
            return null
        }
        
        if (HttpStatus.OK.value() != statusCode) {
            throw RuntimeException("[API error] findCurrentGame: summonerId: $summonerId, statusCode: ${response.statusCode()}, message: ${response.body()}")
        }

        return response.body()?.let {
            logger.info("[API request] findCurrentGame OK, summonerId: $summonerId")
            it.parseJson()
        }?: throw RuntimeException("[API error] findCurrentGame: body is null, summonerId: $summonerId")
    }

    fun fallbackFindCurrentGame(e: Throwable): CurrentGameInfo? {
        logger.error("[API fallback] findCurrentGame: ", e)
        return null
    }

}
