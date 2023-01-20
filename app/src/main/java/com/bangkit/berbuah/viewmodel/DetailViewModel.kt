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
import com.bangkit.berbuah.response.FruitResponse
import com.bangkit.berbuah.ui.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val application: Application) : ViewModel() {

    private val listDetailFruitMutable = MutableLiveData<ArrayList<DetailFruit>>()
    private val favoriteFruitRepository: FavoriteRepository = FavoriteRepository(application)

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    internal fun setDetailFruit(nama: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailFruit(nama)
        client.enqueue(object : Callback<FruitResponse> {

            override fun onResponse(
                call: Call<FruitResponse>,
                response: Response<FruitResponse>
            ) {
                _isLoading.value = false
                val listDetailFruit = ArrayList<DetailFruit>()
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        response.body()?.artikel?.forEach { detailFruit ->
                            listDetailFruit.add(
                                DetailFruit(
                                    detailFruit.nama,
                                    detailFruit.namaLatin,
                                    detailFruit.deskripsi,
                                    detailFruit.image,
                                    detailFruit.manfaat,
                                 //   detailFruit.kandungan
                                )
                            )
                        }
                        listDetailFruitMutable.postValue(listDetailFruit)
                    }
                } else {
                    Toast.makeText(
                        application.applicationContext,
                        response.message(),
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<FruitResponse>, t: Throwable) {
                _isLoading.value = false
                Toast.makeText(
                    application.applicationContext,
                    t.message,
                    Toast.LENGTH_SHORT
                ).show()
                Log.e(TAG, "onFailure ${t.message}")
            }
        })
    }

    internal fun setScanFruit(nama: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getScanFruit(nama)
        client.enqueue(object : Callback<FruitResponse> {

            override fun onResponse(
                call: Call<FruitResponse>,
                response: Response<FruitResponse>
            ) {
                _isLoading.value = false
                val listDetailFruit = ArrayList<DetailFruit>()
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        response.body()?.artikel?.forEach { detailFruit ->
                            listDetailFruit.add(
                                DetailFruit(
                                    detailFruit.nama,
                                    detailFruit.namaLatin,
                                    detailFruit.deskripsi,
                                    detailFruit.image,
                                    detailFruit.manfaat,
                              //      detailFruit.kandungan
                                )
                            )
                        }
                        listDetailFruitMutable.postValue(listDetailFruit)
                    }
                } else {
                    Toast.makeText(
                        application.applicationContext,
                        response.message(),
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<FruitResponse>, t: Throwable) {
                _isLoading.value = false
                Toast.makeText(
                    application.applicationContext,
                    t.message,
                    Toast.LENGTH_SHORT
                ).show()
                Log.e(TAG, "onFailure ${t.message}")
            }
        })
    }

    internal fun getDetailFruit(): LiveData<ArrayList<DetailFruit>> = listDetailFruitMutable

    internal fun check(id: String) = favoriteFruitRepository.check(id)

    internal fun insert(id: String, nama: String, deskripsi: String, gambar: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val fruitItem = Favorite(
                id, nama, deskripsi, gambar
            )
            favoriteFruitRepository.insert(fruitItem)
        }
    }

    internal fun delete(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteFruitRepository.delete(id)
        }
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }
}