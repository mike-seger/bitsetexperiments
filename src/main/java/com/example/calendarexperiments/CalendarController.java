package com.example.calendarexperiments;

import com.example.calendarexperiments.jackson.CustomConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
			calendarService.getHolidaysList(year, country));
	}

	@GetMapping("/public-holidays/{startYear}/{endYear}/{country}")
	public String getHolidays(@PathVariable int startYear, @PathVariable int endYear,
              @PathVariable String country) throws IOException, InterruptedException {
		List<LocalDate> dates = new ArrayList<>();
		for(int year=startYear; year<=endYear; year++) {
			dates.addAll(calendarService.getHolidaysList(year, country));
		}
		Collections.sort(dates);
		return objectMapper.writeValueAsString(dates);
	}
}
