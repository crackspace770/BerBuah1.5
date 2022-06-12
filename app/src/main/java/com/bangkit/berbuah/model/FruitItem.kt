package com.bangkit.berbuah.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FruitItem(
//    val id: String? = null,
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
    val manfaat: List<String>? = null,
    val nutrisi: List<Nutrisi>? = null
) : Parcelable

@Parcelize
data class Nutrisi(
    val kalori: String? = null,
    val karbohidrat: String? = null,
    val gula: String? = null,
    val serat: String? = null,
    val protein: String? = null,
    val air: String? = null,
    val lemak: String? = null
): Parcelable

