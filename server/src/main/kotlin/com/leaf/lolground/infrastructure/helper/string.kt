package com.leaf.lolground.infrastructure.helper

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import mu.KotlinLogging

val logger = KotlinLogging.logger {}

inline fun <reified T: Any> String.parseJson(): T? {
    return try {
        val mapper = jacksonObjectMapper()
        mapper.readValue(this, T::class.java)
    } catch (e: Exception) {
        logger.error("Error - parseJson", e)
        null
    }
}

inline fun <reified T: Any> String.parseJsonList(): List<T>? {
    return try {
        val mapper = jacksonObjectMapper()
        mapper.readValue(this, object: TypeReference<List<T>>() {})
    } catch (e: Exception) {
        logger.error("Error - parseJson", e)
        null
    }
}
