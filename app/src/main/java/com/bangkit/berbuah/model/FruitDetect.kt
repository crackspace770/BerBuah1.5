package com.bangkit.berbuah.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FruitDetect (
    val id : String,
    val nama : String,
    val namaLatin: String,
    val deskripsi : String,
    val manfaat : List<String> ,
    val nutrisi : String,
    val gambar : String
        ): Parcelable