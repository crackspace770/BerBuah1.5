package com.bangkit.berbuah.model

import android.os.Parcelable
import com.bangkit.berbuah.response.Nutrisi
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FruitItem(
    val nama: String? = null,
    val deskripsi: String? = null,
    val gambar: String? = null
) : Parcelable

@Parcelize
data class DetailFruit(
    val id: Int,
    val nama: String,
    val nama_latin: String,
    val deskripsi: String,
    val gambar: String,
    val manfaat: List<String>,
    val nutrisi: Nutrisi
) : Parcelable

