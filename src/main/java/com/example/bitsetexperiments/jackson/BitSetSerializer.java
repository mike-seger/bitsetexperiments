package com.example.bitsetexperiments.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.BitSet;

public class BitSetSerializer extends JsonSerializer<BitSet> {
    @Override
    public void serialize(BitSet value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartArray();
        for (long l : value.toLongArray()) {
            gen.writeNumber(l);
        }
        gen.writeEndArray();
    }

    @Override
    public Class<BitSet> handledType() {
        return BitSet.class;
    }
}

