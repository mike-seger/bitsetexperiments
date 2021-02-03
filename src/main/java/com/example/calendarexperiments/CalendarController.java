package com.example.calendarexperiments;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("ext-api")
public class CalendarController {
	private final ObjectMapper objectMapper=new ObjectMapper().registerModule(new JavaTimeModule());

	@GetMapping("/public-holidays/{year}/{country}")
	public String getHolidays(@PathVariable int year, @PathVariable String country) throws IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create("https://public-holiday.p.rapidapi.com/"+year+"/"+country))
			.header("x-rapidapi-key", "5bd6e4f5a0msh7691fef6f91a012p1c57abjsnd8dc7a89573c")
			.header("x-rapidapi-host", "public-holiday.p.rapidapi.com")
			.method("GET", HttpRequest.BodyPublishers.noBody())
			.build();
		HttpResponse<String> response =
			HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		List<Holiday> holidays = Arrays.asList(objectMapper.readValue(response.body(), Holiday[].class));
		List<LocalDate> dates =
			holidays.stream().filter(h -> h.counties==null || h.counties.size()==0 || h.counties.contains("CH-ZH"))
				.map(h -> h.date).collect(Collectors.toList());
		return objectMapper.writeValueAsString(dates);
	}

	@GetMapping("/public-holidays/{startYear}/{endYear}/{country}")
	public String getHolidays(@PathVariable int startYear, @PathVariable int endYear,
              @PathVariable String country) throws IOException, InterruptedException {
		List<LocalDate> dates = new ArrayList<>();
		for(int year=startYear; year<=endYear; year++) {
			dates.addAll(getHolidaysList(year, country));
		}
		Collections.sort(dates);
		return objectMapper.writeValueAsString(dates);
	}

	private List<LocalDate> getHolidaysList(int year, String country) throws IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create("https://public-holiday.p.rapidapi.com/"+year+"/"+country))
			.header("x-rapidapi-key", "5bd6e4f5a0msh7691fef6f91a012p1c57abjsnd8dc7a89573c")
			.header("x-rapidapi-host", "public-holiday.p.rapidapi.com")
			.method("GET", HttpRequest.BodyPublishers.noBody())
			.build();
		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		List<Holiday> holidays = Arrays.asList(objectMapper.readValue(response.body(), Holiday[].class));
		return holidays.stream().filter(h -> h.counties==null || h.counties.contains("CH-ZH"))
			.map(h -> h.date).collect(Collectors.toList());
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Holiday {
		public LocalDate date;
		public Set<String> counties;
	}
}
