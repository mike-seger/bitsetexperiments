package com.example.calendarexperiments

import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import java.io.IOException
import java.time.Duration
import java.time.LocalDate
import java.util.*

@RestController
@RequestMapping("ext-api")
class Controller(private val objectMapper: CustomObjectMapper, private val service: Service) {

    // Excerpt of James Baldwin "A Letter to my Nephew"
    private val speech = arrayOf(
        "Well,","you","were","born;","here","you","came,","something","like","fifteen","years","ago,","and","though",
        "your","father","and","mother","and","grandmother,","looking","about","the","streets","through","which","they",
        "were","carrying","you,","staring","at","the","walls","into","which","they","brought","you,","had","every",
        "reason","to","be","heavy-hearted,","yet","they","were","not,","for","here","you","were,","big","James,",
        "named","for","me.","You","were","a","big","baby.","I","was","not.","Here","you","were","to","be","loved.",
        "To","be","loved,","baby,","hard","at","once","and","forever","to","strengthen","you","against","the",
        "loveless","world.","Remember","that.","I","know","how","black","it","looks","today","for","you.","It","looked",
        "black","that","day","too.","Yes,","we","were","trembling.","We","have","not","stopped","trembling","yet,",
        "but","if","we","had","not","loved","each","other,","none","of","us","would","have","survived,","and","now",
        "you","must","survive","because","we","love","you","and","for","the","sake","of","your","children","and",
        "your","children's","children."
    )

    @GetMapping(value = ["/speech"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getSpeech(): Flux<String>? {
        return Flux
            .fromArray(speech)
            .delayElements(Duration.ofSeconds(1))
            .repeat()
            .log()
    }

    @GetMapping("/public-holidays/{year}/{country}")
    @Throws(IOException::class, InterruptedException::class)
    fun getHolidays(@PathVariable year: Int, @PathVariable country: String): SortedSet<LocalDate> {
        return service.getHolidays(year, country)
    }

    @GetMapping("/public-holidays/{startYear}/{endYear}/{country}")
    @Throws(IOException::class, InterruptedException::class)
    fun getHolidays(
        @PathVariable startYear: Int,
        @PathVariable endYear: Int,
        @PathVariable country: String
    ): SortedSet<LocalDate> {
        return service.getHolidays(startYear, endYear, country)
    }

    @GetMapping("/public-holiday/{date}/{country}")
    @Throws(IOException::class)
    fun isHoliday(
        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate,
        @PathVariable country: String
    ): Boolean {
        return service.isPublicHoliday(date, country)
    }
}