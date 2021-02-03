package com.example.bitsetexperiments;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.BitSet;

@Configuration
public class JsonConfig extends MappingJackson2HttpMessageConverter {

    public JsonConfig(ObjectMapper objectMapper) {
        super(objectMapper);

        SimpleModule bitSetModule = new SimpleModule("BitSetModule");
        bitSetModule.addSerializer(new BitSetSerializer());
        bitSetModule.addDeserializer(BitSet.class, new BitSetDeserializer());
        objectMapper.registerModule(bitSetModule);
    }
}