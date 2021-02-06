package com.example.calendarexperiments.bitset.jackson

import com.fasterxml.jackson.databind.module.SimpleModule
import java.util.*

class BitSetModule : SimpleModule(BitSetModule::class.java.simpleName) {
    init {
        addSerializer(BitSetSerializer())
        addDeserializer(BitSet::class.java, BitSetDeserializer())
    }
}