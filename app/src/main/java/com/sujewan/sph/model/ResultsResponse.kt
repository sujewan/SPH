package com.sujewan.sph.model

import com.google.gson.annotations.SerializedName

data class ResultsResponse(
    @SerializedName("records")      val records     : ArrayList<Record>
)
