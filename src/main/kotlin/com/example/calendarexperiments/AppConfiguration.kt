package com.example.calendarexperiments

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("app")
data class AppConfiguration (var calendarService: CalendarServiceConfig) {
    @Configuration
    data class CalendarServiceConfig (var publicHolidays: List<String>)
}

@Configuration
class CustomObjectMapper : ObjectMapper() {
    init {
        registerModule(JavaTimeModule())
    }
}