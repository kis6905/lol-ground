package com.leaf.lolground.configuration

import com.leaf.lolground.infrastructure.helper.logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import javax.annotation.PostConstruct

@EnableJpaAuditing
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
        logger.info("[Database] url            : $url")
        logger.info("[Database] username       : $username")
        logger.info("[Database] password length: ${password.length}")
    }

}