package com.sujewan.sph.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.sujewan.sph.model.Quarter
import com.sujewan.sph.model.YearlyRecord
import com.sujewan.sph.util.getValue
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

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

        yearlyRecordDao.insertAllRecords(createMockData())
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

    @Test fun getCharactersOrderedByPage() {
        val recordList = getValue(yearlyRecordDao.getAllRecords())
        assertThat(recordList.size, equalTo(3))

        assertThat(recordList[0], equalTo(recordA))
        assertThat(recordList[1], equalTo(recordB))
        assertThat(recordList[2], equalTo(recordC))
    }


}