package com.example.calendarexperiments.bitset.jackson

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import java.io.IOException
import java.util.*

class BitSetSerializer : JsonSerializer<BitSet>() {
    @Throws(IOException::class)
    override fun serialize(value: BitSet, gen: JsonGenerator, serializers: SerializerProvider) {
        gen.writeStartArray()
        for (l in value.toLongArray()) {
            gen.writeNumber(l)
        }
        gen.writeEndArray()
    }

    override fun handledType(): Class<BitSet> {
        return BitSet::class.java
    }
}