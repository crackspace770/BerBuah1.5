package com.bangkit.berbuah.response

import com.google.gson.annotations.SerializedName

data class SearchResponse (

    @field:SerializedName("nama")
    val nama: String,

    @field:SerializedName("deskripsi")
    val deskripsi: String,

    @field:SerializedName("gambar")
    val gambar: String
)