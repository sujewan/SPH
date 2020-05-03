package com.sujewan.sph.model

import com.google.gson.annotations.SerializedName

data class ResultsResponse(
    @SerializedName("resource_id")  val resource_id : String,
    @SerializedName("records")      val records     : ArrayList<Record>
)
