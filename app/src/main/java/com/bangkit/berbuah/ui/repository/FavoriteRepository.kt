package com.bangkit.berbuah.ui.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.bangkit.berbuah.database.Favorite
import com.bangkit.berbuah.database.FavoriteDao
import com.bangkit.berbuah.database.FavoriteRoomDatabase

class FavoriteRepository(application: Application) {

    private val favoriteDao: FavoriteDao

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        favoriteDao = db.favoriteDao()
    }

    fun getAllFavorite(): LiveData<List<Favorite>> = favoriteDao.getAllFavorite()

    fun insert(favorite: Favorite) = favoriteDao.insert(favorite)

    fun check(id: String) = favoriteDao.check(id)

    fun delete(id: String) = favoriteDao.delete(id)
}