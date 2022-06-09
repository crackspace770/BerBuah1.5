package com.bangkit.berbuah.model

import com.bangkit.berbuah.response.SearchResponse
import com.google.gson.annotations.SerializedName

data class Search(
    @field:SerializedName("data")
    val data: List<SearchResponse>
)
