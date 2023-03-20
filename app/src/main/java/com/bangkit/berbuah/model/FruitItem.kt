package com.bangkit.berbuah.model

import android.os.Parcelable
import com.bangkit.berbuah.response.Kandungan
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class FruitItem(
    val nama: String? = null ,
    val deskripsi: String? = null,
    val gambar: String? = null,
) : Parcelable


@Parcelize
data class SearchItem(
    val nama: String? = null,
    val namaLatin: String? = null,
    val deskripsi: String? = null,
    val gambar: String? = null
): Parcelable

@Parcelize
data class FruitData(
    val id : String? = null,
    val nama : String? = null,
    var confidence: Float = 0F,
    val deskripsi: String? = null,
    val gambar: String? = null
) : Parcelable

@Parcelize
data class DetailFruit(
    var nama: String? = null,
    val namaLatin: String? = null,
    var deskripsi: String? = null,
    var image: String? = null,
    val manfaat: List<String>? = null,
  //  val nutrisi: List<KandunganBuah>
) : Parcelable

@Parcelize
data class KandunganBuah(
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

