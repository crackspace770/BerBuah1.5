package com.bangkit.berbuah.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit.berbuah.database.Favorite
import com.bangkit.berbuah.ui.repository.FavoriteRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val favoriteUserRepository: FavoriteRepository = FavoriteRepository(application)

    fun getAllFavoriteUser(): LiveData<List<Favorite>> =
        favoriteUserRepository.getAllFavorite()
}