package com.example.calendarexperiments;

import com.example.calendarexperiments.jackson.BitSetModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class JsonConfig extends MappingJackson2HttpMessageConverter {
    public JsonConfig(ObjectMapper objectMapper) {
        super(objectMapper.registerModule(new BitSetModule()));
    }
}