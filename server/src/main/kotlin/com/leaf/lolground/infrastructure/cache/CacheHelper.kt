package com.leaf.lolground.infrastructure.cache

import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap

@Component
class CacheHelper {

    private val cacheTimeMap: MutableMap<String, LocalDateTime> = ConcurrentHashMap()

    fun setCachedTime(key: String) {
        cacheTimeMap[key] = LocalDateTime.now()
    }

    fun getCachedTime(key: String): LocalDateTime? {
        return cacheTimeMap[key]
    }
}
