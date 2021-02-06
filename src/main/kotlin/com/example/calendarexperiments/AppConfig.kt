package com.example.calendarexperiments

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("app")
data class AppConfig (var calendarService: CalendarServiceConfig) {
    @Configuration
    data class CalendarServiceConfig (var publicHolidays: List<String>)
}