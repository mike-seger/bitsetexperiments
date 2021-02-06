package com.example.calendarexperiments.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter

@Configuration
class JsonConfig(objectMapper: ObjectMapper) :
    MappingJackson2HttpMessageConverter(objectMapper.registerModule(BitSetModule()))