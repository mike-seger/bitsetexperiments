package com.example.calendarexperiments;

import com.example.calendarexperiments.jackson.CustomConfiguration;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CalendarService {
	private final Logger logger = LoggerFactory.getLogger(CalendarService.class);
	private final CustomConfiguration.CustomObjectMapper objectMapper;
	private final Map<Integer, SortedSet<LocalDate>> publicHolidayYearSets = new HashMap<>();

	public CalendarService(AppConfig appConfig, CustomConfiguration.CustomObjectMapper objectMapper) throws IOException {
		this.objectMapper = objectMapper;
		init(appConfig.calendarService);
	}

	private void init(AppConfig.CalendarService config) throws IOException {
		CollectionType javaType = objectMapper.getTypeFactory()
			.constructCollectionType(List.class, LocalDate.class);
		Set<LocalDate> publicHolidays = new TreeSet<>();
		for(String location : config.publicHolidays) {
			List<LocalDate> readPublicHolidays = objectMapper.readValue(
				getClass().getResourceAsStream(location), javaType);
			publicHolidays.addAll(readPublicHolidays);
			logger.info("Read {} public holidays from {}", readPublicHolidays.size(), location);
		}
		publicHolidays.forEach(d ->
			publicHolidayYearSets.computeIfAbsent(d.getYear(), k -> new TreeSet<>()).add(d));
	}

	public boolean isPublicHoliday(LocalDate date, String country) {
		Set<LocalDate> dates = publicHolidayYearSets.get(date.getYear());
		if(dates==null) return false;
		return dates.contains(date);
	}

	public SortedSet<LocalDate> getHolidays(int year, String country) throws IOException, InterruptedException {
		SortedSet<LocalDate> result = publicHolidayYearSets.get(year);
		if(result != null) return result; // return preloaded holidays
		logger.info("Requesting holidays for {} / {}", year, country);
		HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create("https://public-holiday.p.rapidapi.com/"+year+"/"+country))
			.header("x-rapidapi-key", "5bd6e4f5a0msh7691fef6f91a012p1c57abjsnd8dc7a89573c")
			.header("x-rapidapi-host", "public-holiday.p.rapidapi.com")
			.method("GET", HttpRequest.BodyPublishers.noBody())
			.build();
		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		List<Holiday> holidays = Arrays.asList(objectMapper.readValue(response.body(), Holiday[].class));
		return holidays.stream().filter(h -> h.counties == null || h.counties.contains("CH-ZH"))
			.map(h -> h.date).collect(Collectors.toCollection(TreeSet::new));
	}

	public SortedSet<LocalDate> getHolidays(int startYear, int endYear, String country) throws IOException, InterruptedException {
		SortedSet<LocalDate> dates = new TreeSet<>();
		for(int year=startYear; year<=endYear; year++) {
			dates.addAll(getHolidays(year, country));
		}
		return dates;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Holiday {
		public LocalDate date;
		public Set<String> counties;
	}
}
