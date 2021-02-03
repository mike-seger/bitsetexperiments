package com.example.bitsetservice

import java.time.Duration
import java.time.LocalDate
import java.util.*

class Cache(var start: LocalDate, var end: LocalDate) {
    var bitSet: BitSet = BitSet(offset(start, end))

    fun setDay(day: LocalDate) {
        bitSet.set(offset(start, day))
    }

    fun getDay(day: LocalDate) : Boolean {
        return bitSet[offset(start, day)]
    }

    private fun offset(start: LocalDate, end: LocalDate): Int {
        return Duration.between(start.atStartOfDay(), end.atStartOfDay()).toDays().toInt()
    }
}
