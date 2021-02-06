@file:Suppress("unused")

package com.example.calendarexperiments.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.context.annotation.Configuration

@Configuration
class CustomConfiguration {

    val objectMapper: CustomObjectMapper
        get() = CustomObjectMapper()

    @Configuration
    class CustomObjectMapper : ObjectMapper() {
        init {
            registerModule(JavaTimeModule())
        }
    }
}