package com.example.calendarexperiments.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomConfiguration {

	@Bean
	public CustomObjectMapper getObjectMapper() {
		return new CustomObjectMapper();
	}

	public static class CustomObjectMapper extends ObjectMapper {
		public CustomObjectMapper() {
			registerModule(new JavaTimeModule());
		}
	}
}
