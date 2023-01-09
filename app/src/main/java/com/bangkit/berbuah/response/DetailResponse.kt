package com.bangkit.berbuah.response

import com.google.gson.annotations.SerializedName

data class DetailResponse(

    @field:SerializedName("nama")
    val nama: String,

    @field:SerializedName("namaLatin")
    val namaLatin: String,

    @field:SerializedName("deskripsi")
    val deskripsi: String,

    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("manfaat")
    val manfaat: List<String>,

  //  @field:SerializedName("kandungan")
  //  val kandungan: List<Kandungan>
)


