package com.sujewan.sph.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.sujewan.sph.model.Quarter
import com.sujewan.sph.model.YearlyRecord
import com.sujewan.sph.util.getValue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
class YearlyRecordDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var yearlyRecordDao: YearlyRecordDao
    private lateinit var recordA: YearlyRecord
    private lateinit var recordB: YearlyRecord
    private lateinit var recordC: YearlyRecord

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDB() {
        val context = InstrumentationRegistry.getTargetContext()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        yearlyRecordDao = database.yearlyRecordDao()
    }

    private fun createMockData(): ArrayList<YearlyRecord> {
        val quartersA = arrayListOf<Quarter?>()
        quartersA.add(Quarter(15, 2008, "Quarter 01",0.171586f, false))
        quartersA.add(Quarter(16, 2008, "Quarter 02",0.248899f, false))
        quartersA.add(Quarter(17, 2008, "Quarter 03",0.439655f, false))
        quartersA.add(Quarter(18, 2008, "Quarter 04",0.683579f, false))

        val quartersB = arrayListOf<Quarter?>()
        quartersB.add(Quarter(15, 2011, "Quarter 01",3.466228f, false))
        quartersB.add(Quarter(16, 2011, "Quarter 02",3.380723f, true))
        quartersB.add(Quarter(17, 2011, "Quarter 03",3.713792f, false))
        quartersB.add(Quarter(18, 2011, "Quarter 04",4.07796f, false))

        val quartersC = arrayListOf<Quarter?>()
        quartersC.add(Quarter(15, 2018, "Quarter 01",9.687363f, false))
        quartersC.add(Quarter(16, 2018, "Quarter 02",9.98677f, false))
        quartersC.add(Quarter(17, 2018, "Quarter 03",10.902194f, false))
        quartersC.add(Quarter(18, 2018, "Quarter 04",11.677166f, false))

        recordA = YearlyRecord(2008, 1.543719f, false, quartersA)
        recordB = YearlyRecord(2011, 14.638703f, true, quartersB)
        recordC = YearlyRecord(2018, 42.253494f, false, quartersC)

        return arrayListOf<YearlyRecord>(recordA, recordB, recordC)
    }

    @After
    fun closeDB() {
        database.close()
    }

    @Test
    fun testSaveAllRecords() {
        // Check DB is empty
        val initialRecords = getValue(yearlyRecordDao.getAllRecords())
        assertEquals(0, initialRecords.size)

        // Save Mock Records to DB
        val mockEntities = createMockData()
        yearlyRecordDao.insertAllRecords(mockEntities)

        // Get Records and Compare
        val recordedEntities = getValue(yearlyRecordDao.getAllRecords())
        assertEquals(mockEntities.size, recordedEntities.size)

        // Check random data
        assertEquals(recordedEntities[1], recordB)
    }

    @Test
    fun testGetAllRecords() {
        // Check DB is empty
        val initialRecords = getValue(yearlyRecordDao.getAllRecords())
        assertEquals(0, initialRecords.size)

        // Save Mock Records to DB
        val mockEntities = createMockData()
        yearlyRecordDao.insertAllRecords(mockEntities)

        // Get Records and Compare
        val recordedEntities = getValue(yearlyRecordDao.getAllRecords())
        assertEquals(mockEntities.size, recordedEntities.size)

        assertEquals(recordedEntities[0], recordA)
        assertEquals(recordedEntities[1], recordB)
        assertEquals(recordedEntities[2], recordC)
    }

    @Test
    fun testGetRecordById() {
        // Check DB is empty
        val initialRecords = getValue(yearlyRecordDao.getAllRecords())
        assertEquals(0, initialRecords.size)

        // Save Mock Records to DB
        val mockEntities = createMockData()
        yearlyRecordDao.insertAllRecords(mockEntities)

        // Get Store Records and Compare
        val recordedEntities01 = getValue(yearlyRecordDao.getRecordByYear(recordA.year))
        assertEquals(recordedEntities01, recordA)
        assertEquals(recordedEntities01.year, recordA.year)
        assertEquals(recordedEntities01.isDecreasedYear, recordA.isDecreasedYear)
        assertEquals(recordedEntities01.totalVolume, recordA.totalVolume)
        assertEquals(recordedEntities01.quarters, recordA.quarters)
        assertEquals(recordedEntities01.quarters.size, recordA.quarters.size)
        assertEquals(recordedEntities01.quarters[0]?.id, recordA.quarters[0]?.id)

        val recordedEntities02 = getValue(yearlyRecordDao.getRecordByYear(recordB.year))
        assertEquals(recordedEntities02, recordB)
        assertEquals(recordedEntities02.year, recordB.year)
        assertEquals(recordedEntities02.isDecreasedYear, recordB.isDecreasedYear)
        assertEquals(recordedEntities02.totalVolume, recordB.totalVolume)
        assertEquals(recordedEntities02.quarters, recordB.quarters)
        assertEquals(recordedEntities02.quarters.size, recordB.quarters.size)
        assertEquals(recordedEntities02.quarters[0]?.id, recordB.quarters[0]?.id)

        val recordedEntities03 = getValue(yearlyRecordDao.getRecordByYear(recordC.year))
        assertEquals(recordedEntities03, recordC)
        assertEquals(recordedEntities03.year, recordC.year)
        assertEquals(recordedEntities03.isDecreasedYear, recordC.isDecreasedYear)
        assertEquals(recordedEntities03.totalVolume, recordC.totalVolume)
        assertEquals(recordedEntities03.quarters, recordC.quarters)
        assertEquals(recordedEntities03.quarters.size, recordC.quarters.size)
        assertEquals(recordedEntities03.quarters[0]?.id, recordC.quarters[0]?.id)
    }
}