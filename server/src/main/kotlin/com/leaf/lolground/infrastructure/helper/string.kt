package com.leaf.lolground.infrastructure.helper

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import mu.KotlinLogging
import kotlin.reflect.KClass

private val logger = KotlinLogging.logger {}

fun <T: Any> String.parseJson(clazz: KClass<T>): T? =
    try {
        val mapper = jacksonObjectMapper()
        mapper.readValue(this, clazz.java)
    } catch (e: Exception) {
        logger.error("Error - parseJson", e)
        null
    }
