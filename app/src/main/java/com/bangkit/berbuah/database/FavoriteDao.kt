package com.bangkit.berbuah.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {

    @Insert
    fun insert(favorite: Favorite)

    @Query("SELECT count(*) FROM favorite WHERE favorite.id = :id")
    fun check(id: String): Int

    @Query("DELETE FROM favorite WHERE favorite.id = :id")
    fun delete(id: String): Int

    @Query("SELECT * FROM favorite")
    fun getAllFavorite(): LiveData<List<Favorite>>
}