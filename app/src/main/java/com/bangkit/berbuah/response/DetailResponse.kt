package com.bangkit.berbuah.response

import com.google.gson.annotations.SerializedName

data class DetailResponse(

    @field:SerializedName("nama")
    val nama: String,

    @field:SerializedName("nama_latin")
    val nama_latin: String,

    @field:SerializedName("deskripsi")
    val deskripsi: String,

    @field:SerializedName("gambar")
    val gambar: String,

    @field:SerializedName("manfaat")
    val manfaat: List<String>,

    @field:SerializedName("nutrisi")
    val nutrisi: List<Nutrisi>
)

data class Nutrisi(

    @field:SerializedName("kalori")
    val kalori: String,

    @field:SerializedName("karbohidrat")
    val karbohidrat: String,

    @field:SerializedName("gula")
    val gula: String,

    @field:SerializedName("protein")
    val protein: String,

    @field:SerializedName("air")
    val air: String,

    @field:SerializedName("lemak")
    val lemak: String,

    @field:SerializedName("serat")
    val serat: String,

    @field:SerializedName("kalsium")
    val kalsium: String,

    @field:SerializedName("fosfor")
    val fosfor: String
)