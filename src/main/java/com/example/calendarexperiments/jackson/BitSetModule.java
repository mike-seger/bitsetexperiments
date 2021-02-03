package com.example.calendarexperiments.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;

import java.util.BitSet;

public class BitSetModule extends SimpleModule {
	public BitSetModule() {
		super(BitSetModule.class.getSimpleName());
		addSerializer(new BitSetSerializer());
		addDeserializer(BitSet.class, new BitSetDeserializer());
	}
}
