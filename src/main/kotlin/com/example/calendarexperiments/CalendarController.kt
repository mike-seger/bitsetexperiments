package com.example.calendarexperiments

import com.example.calendarexperiments.jackson.CustomConfiguration.CustomObjectMapper
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.IOException
import java.time.LocalDate

@RestController
@RequestMapping("ext-api")
class CalendarController(private val objectMapper: CustomObjectMapper, private val calendarService: CalendarService) {
    @GetMapping("/public-holidays/{year}/{country}")
    @Throws(IOException::class, InterruptedException::class)
    fun getHolidays(@PathVariable year: Int, @PathVariable country: String?): String {
        return objectMapper.writeValueAsString(
            calendarService.getHolidays(year, country!!)
        )
    }

    @GetMapping("/public-holiday/{date}/{country}")
    @Throws(IOException::class)
    fun isHoliday(
        @PathVariable date: LocalDate?,
        @PathVariable country: String?
    ): String {
        return objectMapper.writeValueAsString(calendarService.isPublicHoliday(date!!, country))
    }

    @GetMapping("/public-holidays/{startYear}/{endYear}/{country}")
    @Throws(IOException::class, InterruptedException::class)
    fun getHolidays(
        @PathVariable startYear: Int,
        @PathVariable endYear: Int,
        @PathVariable country: String?
    ): String {
        return objectMapper.writeValueAsString(calendarService.getHolidays(startYear, endYear, country!!))
    }
}