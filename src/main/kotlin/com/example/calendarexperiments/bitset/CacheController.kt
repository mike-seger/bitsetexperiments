package com.example.calendarexperiments.bitset

import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("bitset")
class CacheController(var cacheService: CacheService) {
    @GetMapping("/cache")
    fun cache(): Cache {
        return cacheService.getCache()
    }

    @GetMapping("/cache/{date}")
    fun getDateValidity(@PathVariable("date") date: LocalDate): Boolean {
        return cacheService.getDay(date)
    }

    @PutMapping("/cache/{date}")
    fun setDateValidity(@PathVariable("date") date: LocalDate) {
        cacheService.setDay(date)
    }
}
