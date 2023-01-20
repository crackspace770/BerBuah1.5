package com.bangkit.berbuah.response

import com.google.gson.annotations.SerializedName

data class FruitResponse(

	@field:SerializedName("artikel")
	val artikel: List<ArtikelItem>
)

data class ArtikelItem(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("manfaat")
	val manfaat: List<String>,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("nama_latin")
	val namaLatin: String,

	@field:SerializedName("deskripsi")
	val deskripsi: String,

	@field:SerializedName("id")
	val id: String,

//	  @field:SerializedName("kandungan")
//	  val kandungan: List<Kandungan>

)


data class Kandungan(

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




