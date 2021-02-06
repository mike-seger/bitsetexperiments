package com.example.calendarexperiments;

import com.example.calendarexperiments.jackson.CustomConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("ext-api")
public class CalendarController {
	private final CustomConfiguration.CustomObjectMapper objectMapper;
	private final CalendarService calendarService;

	public CalendarController(CustomConfiguration.CustomObjectMapper objectMapper, CalendarService calendarService) {
		this.objectMapper = objectMapper;
		this.calendarService = calendarService;
	}

	@GetMapping("/public-holidays/{year}/{country}")
	public String getHolidays(@PathVariable int year, @PathVariable String country) throws IOException, InterruptedException {
		return objectMapper.writeValueAsString(
			calendarService.getHolidays(year, country));
	}

	@GetMapping("/public-holiday/{date}/{country}")
	public String isHoliday(@PathVariable LocalDate date,
			  @PathVariable String country) throws IOException {
		return objectMapper.writeValueAsString(calendarService.isPublicHoliday(date, country));
	}

	@GetMapping("/public-holidays/{startYear}/{endYear}/{country}")
	public String getHolidays(@PathVariable int startYear, @PathVariable int endYear,
              @PathVariable String country) throws IOException, InterruptedException {
		return objectMapper.writeValueAsString(calendarService.getHolidays(startYear, endYear, country));
	}
}
