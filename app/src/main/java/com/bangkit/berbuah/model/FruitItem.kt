package com.bangkit.berbuah.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class FruitItem(
    val id: String? = null,
    val nama: String? = null,
    val namaLatin: String?,
    val deskripsi: String? = null,
    val manfaat: String,
    val kandungan: List<String>,
    val gambar: String? = null
) : Parcelable

data class FruitData(
    val id : Int,
    val nama : String,
    val namaLatin: String?,
    val deskripsi : String,
    val manfaat : List<String>? = null,
    val kandungan : String,
    val gambar : String
) : Serializable

@Parcelize
data class DetailFruit(
    val id : String,
    val nama: String? = null,
    val namaLatin: String? = null,
    val deskripsi: String? = null,
    val image: String? = null,
    val manfaat: List<String>? = null,
    val kandungan: List<Kandungan>? = null
) : Parcelable

//@Parcelize
//data class Manfaat(
//    val nama: String? = null
//):Parcelable


@Parcelize
data class Kandungan(
    val kalori: String? = null,
    val karbohidrat: String? = null,
    val gula: String? = null,
    val serat: String? = null,
    val protein: String? = null,
    val air: String? = null,
    val lemak: String? = null,
    val kalsium: String? = null,
    val fosfor: String? = null
): Parcelable

