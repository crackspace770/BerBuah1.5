package com.bangkit.berbuah.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.berbuah.database.Favorite
import com.bangkit.berbuah.model.UserPreferences
import com.bangkit.berbuah.ui.repository.FavoriteRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : ViewModel() {
    private val favoriteUserRepository: FavoriteRepository = FavoriteRepository(application)

    fun getAllFavoriteUser(): LiveData<List<Favorite>> =
        favoriteUserRepository.getAllFavorite()
}