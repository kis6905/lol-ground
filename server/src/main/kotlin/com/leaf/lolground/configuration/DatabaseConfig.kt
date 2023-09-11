package com.leaf.lolground.configuration

import com.leaf.lolground.infrastructure.helper.logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct

@Configuration
class DatabaseConfig(
    @Value("\${spring.datasource.url}")
    private val url: String,
    @Value("\${spring.datasource.username}")
    private val username: String,
    @Value("\${spring.datasource.password}")
    private val password: String,
) {

    @PostConstruct
    fun postConstruct() {
        logger.info("[Database] url     : $url")
        logger.info("[Database] username: $username")
    }

}