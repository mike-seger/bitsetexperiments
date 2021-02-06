package com.example.calendarexperiments

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.IOException
import java.time.LocalDate

@RestController
@RequestMapping("ext-api")
class Controller(private val objectMapper: CustomObjectMapper, private val service: Service) {
    @GetMapping("/public-holidays/{year}/{country}")
    @Throws(IOException::class, InterruptedException::class)
    fun getHolidays(@PathVariable year: Int, @PathVariable country: String): String {
        return objectMapper.writeValueAsString(
            service.getHolidays(year, country)
        )
    }

    @GetMapping("/public-holidays/{startYear}/{endYear}/{country}")
    @Throws(IOException::class, InterruptedException::class)
    fun getHolidays(
        @PathVariable startYear: Int,
        @PathVariable endYear: Int,
        @PathVariable country: String
    ): String {
        return objectMapper.writeValueAsString(service.getHolidays(startYear, endYear, country))
    }

    @GetMapping("/public-holiday/{date}/{country}")
    @Throws(IOException::class)
    fun isHoliday(
        @PathVariable date: LocalDate,
        @PathVariable country: String
    ): String {
        return objectMapper.writeValueAsString(service.isPublicHoliday(date, country))
    }
}