package com.example.calendarexperiments

import com.example.calendarexperiments.bitset.jackson.BitSetModule
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter

@Configuration
@ConfigurationProperties("app")
data class AppConfiguration (var calendarService: CalendarServiceConfig) {
    @Configuration
    data class CalendarServiceConfig (var publicHolidays: List<String>)
}

@Configuration
class JsonConfig(objectMapper: ObjectMapper) :
    MappingJackson2HttpMessageConverter(objectMapper.registerModule(BitSetModule()))

@Configuration
class CustomObjectMapper : ObjectMapper() {
    init {
        registerModule(JavaTimeModule())
    }
}