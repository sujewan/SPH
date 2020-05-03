package com.sujewan.sph.api

import androidx.lifecycle.LiveData
import com.sujewan.sph.model.DataUsageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DataUsageApi {
    @GET("action/datastore_search")
    fun getMobileDataUsage(
        @Query("resource_id") resourceId: String?): LiveData<ApiResponse<DataUsageResponse>>
}