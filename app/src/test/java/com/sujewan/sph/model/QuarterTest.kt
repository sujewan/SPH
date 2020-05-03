package com.sujewan.sph.model

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class QuarterTest {
    lateinit var quarter: Quarter

    @Before
    fun setUp() {
        quarter = Quarter(15, 2008, "Quarter 01",0.171586f, false)
    }

    @Test
    fun testId() {
        assertEquals(quarter.id, 15)
    }

    @Test
    fun testYear() {
        assertEquals(quarter.year, 2008)
    }

    @Test
    fun testQuarterName() {
        assertEquals(quarter.quarterName, "Quarter 01")
    }

    @Test
    fun testVolume() {
        assertEquals(quarter.volume, 0.171586f)
    }

    @Test
    fun testIsDecrease() {
        assertEquals(quarter.isDecrease, false)
    }
}