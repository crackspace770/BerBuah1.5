package com.bangkit.berbuah.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class FruitItem(
//    val id: String? = null,
    val nama: String? = null,
    val deskripsi: String? = null,
    val gambar: String? = null
) : Parcelable

data class FruitData(
    val id : Int,
    val nama : String,
    val deskripsi : String,
    val asal : String,
    val manfaat : String,
    val nutrisi : String,
    val gambar : String
) : Serializable

@Parcelize
data class DetailFruit(
    val nama: String? = null,
    val nama_latin: String? = null,
    val deskripsi: String? = null,
    val gambar: String? = null,
    val manfaat: List<String>? = null,
    val nutrisi: List<Nutrisi>? = null
) : Parcelable

//@Parcelize
//data class Manfaat(
//    val nama: String? = null
//):Parcelable


@Parcelize
data class Nutrisi(
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

