package com.bangkit.berbuah.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FruitItem(
    val nama: String? = null,
    val deskripsi: String? = null,
    val gambar: String? = null
) : Parcelable

@Parcelize
data class DetailFruit(
    val nama: String? = null,
    val nama_latin: String? = null,
    val deskripsi: String? = null,
    val gambar: String? = null,
    val manfaat: List<String>? = null
//    val nutrisi: Nutrisi? = null
) : Parcelable

@Parcelize
data class Nutrisi(
    val kalori: String,
    val karbohidrat: String,
    val gula: String,
    val serat: String,
    val protein: String,
    val air: String,
    val lemak: String
): Parcelable

