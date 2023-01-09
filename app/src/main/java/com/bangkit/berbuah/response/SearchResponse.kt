package com.bangkit.berbuah.response

import com.google.gson.annotations.SerializedName

data class SearchResponse (

    @field:SerializedName("items")
    val data: List<SearchItem>

)

data class SearchItem (

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("nama")
    val nama: String,

    @field:SerializedName("deskripsi")
    val deskripsi: String,

    @field:SerializedName("image")
    val image: String
)