package com.example.calendarexperiments.jackson

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.google.common.primitives.Longs
import java.io.IOException
import java.util.*

class BitSetDeserializer : JsonDeserializer<BitSet>() {
    @Throws(IOException::class)
    override fun deserialize(jsonParser: JsonParser, deserializationContext: DeserializationContext): BitSet {
        val l = ArrayList<Long>()
        var token: JsonToken
        while (JsonToken.END_ARRAY != jsonParser.nextValue().also { token = it }) {
            if (token.isNumeric) {
                l.add(jsonParser.longValue)
            }
        }
        return BitSet.valueOf(Longs.toArray(l))
    }
}