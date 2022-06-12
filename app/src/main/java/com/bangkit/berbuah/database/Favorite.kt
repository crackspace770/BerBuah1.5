package com.bangkit.berbuah.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Favorite (

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "nama")
    var name: String? = null,

    @ColumnInfo(name = "deskripsi")
    var deskripsi: String? = null,

    @ColumnInfo(name = "gambar")
    var gambar: String? = null

): Parcelable