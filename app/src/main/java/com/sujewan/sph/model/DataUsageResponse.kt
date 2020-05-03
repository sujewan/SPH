package com.sujewan.sph.model

import com.google.gson.annotations.SerializedName

data class DataUsageResponse(
    @SerializedName("help")     val help    : String,
    @SerializedName("success")  val success : Boolean,
    @SerializedName("result")   val result  : ResultsResponse
)