package com.bangkit.berbuah.model

import com.bangkit.berbuah.response.DetailResponse
import com.google.gson.annotations.SerializedName

data class Detail(
    @field:SerializedName("data")
    val data: List<DetailResponse>
)
