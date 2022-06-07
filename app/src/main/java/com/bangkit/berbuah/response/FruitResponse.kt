package com.bangkit.berbuah.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class FruitResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("data")
	val data: List<DataItem?>? = null
)

@Parcelize
data class DataItem(

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("nama_latin")
	val namaLatin: String? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("gambar")
	val gambar: String? = null,

	@field:SerializedName("manfaat")
	val manfaat: List<String?>? = null,

	@field:SerializedName("nutrisi")
	val nutrisi: Nutrisi? = null
): Parcelable

@Parcelize
data class Nutrisi(

	@field:SerializedName("kalori")
	val kalori: String? = null,

	@field:SerializedName("karbohidrat")
	val karbohidrat: String? = null,

	@field:SerializedName("gula")
	val gula: String? = null,

	@field:SerializedName("serat")
	val serat: String? = null,

	@field:SerializedName("protein")
	val protein: String? = null,

	@field:SerializedName("air")
	val air: String? = null,

	@field:SerializedName("lemak")
	val lemak: String? = null
): Parcelable
