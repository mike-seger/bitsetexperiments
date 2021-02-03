package com.example.bitsetservice

import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class CacheService {
    private val cache = Cache(LocalDate.now().minusDays(10000), LocalDate.now())
    fun setDay(day: LocalDate) {
        cache.setDay(day)
    }

    fun getDay(day: LocalDate) : Boolean {
        return cache.getDay(day)
    }

    fun getCache() : Cache {
        return cache
    }
}