package com.leaf.lolground.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.http.HttpClient

@Configuration
class LoLGroundConfig {

    @Bean
    fun httpClient(): HttpClient =
        HttpClient.newHttpClient()

}