package com.leaf.lolground.infrastructure.api.lol

import org.springframework.beans.factory.annotation.Value
import java.net.http.HttpRequest
import java.time.Duration

open class BaseLoLApi {

    companion object {
        const val API_TOKEN_HEADER_NAME = "X-Riot-Token"
    }

    @Value("\${lol.api.token}")
    protected lateinit var apiToken: String

    protected fun makeHttpRequestBuilder(): HttpRequest.Builder {
        return HttpRequest.newBuilder()
            .timeout(Duration.ofSeconds(5))
    }

}