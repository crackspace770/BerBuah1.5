package com.bangkit.berbuah.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.berbuah.api.ApiConfig
import com.bangkit.berbuah.database.Favorite
import com.bangkit.berbuah.model.DetailFruit
import com.bangkit.berbuah.model.FruitDetect
import com.bangkit.berbuah.response.FruitResponse
import com.bangkit.berbuah.ui.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultViewModel(private val application: Application):ViewModel() {

    private val favoriteFruitRepository: FavoriteRepository = FavoriteRepository(application)
    private val listDetailFruitMutable = MutableLiveData<ArrayList<FruitDetect>>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "ResultViewModel"
    }



    internal fun getDetailFruit(): LiveData<ArrayList<FruitDetect>> = listDetailFruitMutable

    internal fun check(id: String) = favoriteFruitRepository.check(id)


    fun insert(id: String, nama: String, deskripsi: String, gambar: String){
        viewModelScope.launch(Dispatchers.IO) {
            val favorite= Favorite(
                id, nama,  deskripsi, gambar
            )
            favoriteFruitRepository.insert(favorite)
        }
    }

    internal fun delete(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteFruitRepository.delete(id)
        }
    }



}