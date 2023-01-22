package com.bangkit.berbuah.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.berbuah.database.Favorite
import com.bangkit.berbuah.ui.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ResultViewModel(private val application: Application):ViewModel() {

    private val favoriteFruitRepository: FavoriteRepository = FavoriteRepository(application)

    internal fun check(id: String) = favoriteFruitRepository.check(id)

    internal fun insert(id: String, nama: String, deskripsi: String, gambar: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val fruitItem = Favorite(
                id, nama,  deskripsi, gambar
            )
            favoriteFruitRepository.insert(fruitItem)
        }
    }

    internal fun delete(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteFruitRepository.delete(id)
        }
    }

}