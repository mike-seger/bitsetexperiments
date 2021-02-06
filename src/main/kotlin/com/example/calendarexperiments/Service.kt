package com.example.calendarexperiments

import com.example.calendarexperiments.AppConfiguration.CalendarServiceConfig
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.slf4j.LoggerFactory
import java.io.IOException
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.LocalDate
import java.util.*
import java.util.function.Consumer
import java.util.stream.Collectors

@org.springframework.stereotype.Service
class Service(appConfig: AppConfiguration, private val objectMapper: CustomObjectMapper) {
    private val logger = LoggerFactory.getLogger(Service::class.java)
    private val publicHolidayYearSets: MutableMap<Int, SortedSet<LocalDate>> = HashMap()

    @Throws(IOException::class)
    private fun init(config: CalendarServiceConfig) {
        val javaType = objectMapper.typeFactory
            .constructCollectionType(MutableList::class.java, LocalDate::class.java)
        val publicHolidays: MutableSet<LocalDate> = TreeSet()
        for (location in config.publicHolidays) {
            val readPublicHolidays = objectMapper.readValue<List<LocalDate>>(
                javaClass.getResourceAsStream(location), javaType
            )
            publicHolidays.addAll(readPublicHolidays)
            logger.info("Read {} public holidays from {}", readPublicHolidays.size, location)
        }
        publicHolidays.forEach(Consumer { d: LocalDate ->
            publicHolidayYearSets.computeIfAbsent(d.year) { TreeSet() }
                .add(d)
        })
    }

    fun isPublicHoliday(date: LocalDate, country: String): Boolean {
        val dates = publicHolidayYearSets[date.year] ?: return false
        return dates.contains(date)
    }

    @Throws(IOException::class, InterruptedException::class)
    fun getHolidays(year: Int, country: String): SortedSet<LocalDate> {
        val result = publicHolidayYearSets[year]
        if (result != null) return result // return preloaded holidays
        logger.info("Requesting holidays for {} / {}", year, country)
        val request = HttpRequest.newBuilder()
            .uri(URI.create("https://public-holiday.p.rapidapi.com/$year/$country"))
            .header("x-rapidapi-key", "5bd6e4f5a0msh7691fef6f91a012p1c57abjsnd8dc7a89573c")
            .header("x-rapidapi-host", "public-holiday.p.rapidapi.com")
            .method("GET", HttpRequest.BodyPublishers.noBody())
            .build()
        val response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString())
        val holidays = listOf(*objectMapper.readValue(response.body(), Array<Holiday>::class.java))
        return holidays.stream().filter { h: Holiday -> h.counties == null || h.counties!!.contains("CH-ZH") }
            .map { h: Holiday -> h.date }.collect(
                Collectors.toCollection { TreeSet() }
            )
    }

    @Throws(IOException::class, InterruptedException::class)
    fun getHolidays(startYear: Int, endYear: Int, country: String): SortedSet<LocalDate> {
        val dates: SortedSet<LocalDate> = TreeSet()
        for (year in startYear..endYear) {
            dates.addAll(getHolidays(year, country))
        }
        return dates
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    class Holiday {
        var date: LocalDate? = null
        var counties: Set<String>? = null
    }

    init {
        init(appConfig.calendarService)
    }
}