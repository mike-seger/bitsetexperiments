package com.example.calendarexperiments;

import com.example.calendarexperiments.jackson.CustomConfiguration;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CalendarService {
	private final CustomConfiguration.CustomObjectMapper objectMapper;
	public CalendarService(CustomConfiguration.CustomObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public List<LocalDate> getHolidaysList(int year, String country) throws IOException, InterruptedException {
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
