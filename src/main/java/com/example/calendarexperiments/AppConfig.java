package com.example.calendarexperiments;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties("app")
public class AppConfig {
	public CalendarService calendarService;

	public CalendarService getCalendarService() {
		return calendarService;
	}

	public void setCalendarService(CalendarService calendarService) {
		this.calendarService = calendarService;
	}

	public static class CalendarService {
		List<String> publicHolidays;

		public List<String> getPublicHolidays() {
			return publicHolidays;
		}

		public void setPublicHolidays(List<String> publicHolidays) {
			this.publicHolidays = publicHolidays;
		}
	}
}
