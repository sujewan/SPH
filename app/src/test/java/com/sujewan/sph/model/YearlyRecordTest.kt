package com.sujewan.sph.model

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class YearlyRecordTest {

    lateinit var quarters: ArrayList<Quarter?>
    lateinit var record: YearlyRecord

    @Before
    fun setUp() {
        quarters = arrayListOf<Quarter?>()
        quarters.add(Quarter(15, 2018, "Quarter 01",9.687363f, false))
        quarters.add(Quarter(16, 2018, "Quarter 02",9.98677f, false))
        quarters.add(Quarter(17, 2018, "Quarter 03",10.902194f, false))
        quarters.add(Quarter(18, 2018, "Quarter 04",11.677166f, false))

        record = YearlyRecord(2008, 1.543719f, false, quarters)
    }

    @Test
    fun testYear() {
        assertEquals(record.year, 2008)
    }

    @Test
    fun testTotalVolume() {
        assertEquals(record.totalVolume, 1.543719f)
    }

    @Test
    fun testIsDecreasedYear() {
        assertEquals(record.isDecreasedYear, false)
    }

    @Test
    fun testQuarters() {
        assertEquals(record.quarters, quarters)
    }
}