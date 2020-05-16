package com.sujewan.sph.repository

import androidx.lifecycle.LiveData
import com.sujewan.sph.api.ApiResponse
import com.sujewan.sph.api.DataUsageApi
import com.sujewan.sph.api.Resource
import com.sujewan.sph.model.DataUsageResponse
import com.sujewan.sph.model.Quarter
import com.sujewan.sph.model.Record
import com.sujewan.sph.model.YearlyRecord
import com.sujewan.sph.room.YearlyRecordDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataUsageRepository @Inject
constructor(val yearlyRecordDao: YearlyRecordDao, private val dataUsageApi: DataUsageApi) {

    fun getMobileDataUsage(resourceId: String): LiveData<Resource<List<YearlyRecord>>> {

        return object : NetworkBoundResource<List<YearlyRecord>, DataUsageResponse>() {

            override fun saveFetchData(item: DataUsageResponse) {
                val recordList = item.result.records
                recordList.let { list ->
                    yearlyRecordDao.insertAllRecords(manipulateDataUsageInfo(list))
                }
            }

            override fun shouldFetch(data: List<YearlyRecord>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<YearlyRecord>> {
                return yearlyRecordDao.getAllRecords()
            }

            override fun fetchService(): LiveData<ApiResponse<DataUsageResponse>> {
                return dataUsageApi.getMobileDataUsage(resourceId)
            }
        }.asLiveData

    }

    private fun manipulateDataUsageInfo(recordList: ArrayList<Record>): ArrayList<YearlyRecord> {
        var quarterList: ArrayList<Quarter?> = arrayListOf()
        val yearlyRecordListWrapper: ArrayList<YearlyRecord> = arrayListOf()

        val yearStartIndex = 2008
        val yearEndIndex = 2018
        var totalVolume = 0.0f
        var previousUsage = 0.0f
        var isDecreasedYear = false

        recordList.forEach {
            val splitArray = it.quarter.split("-")
            val year: Int = splitArray[0].toInt()
            val quarterName: String = splitArray[1]

            if (year in yearStartIndex..yearEndIndex) {
                quarterList.add(
                    Quarter(it.id, year, quarterName.replace("Q", "Quarter 0"), it.volume,
                        previousUsage > it.volume)
                )
                if(previousUsage > it.volume) {
                    isDecreasedYear = true
                }
                previousUsage = it.volume
                totalVolume += it.volume
                if ("Q4" == quarterName) {
                    yearlyRecordListWrapper.add(YearlyRecord(year, totalVolume, isDecreasedYear, quarterList))
                    quarterList = arrayListOf()
                    previousUsage = 0.0f
                    totalVolume = 0.0f
                    isDecreasedYear = false
                }
            }
        }

        return yearlyRecordListWrapper
    }
}