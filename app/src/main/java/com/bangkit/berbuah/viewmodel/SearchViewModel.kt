package com.bangkit.berbuah.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.berbuah.api.ApiConfig
import com.bangkit.berbuah.model.FruitItem
import com.bangkit.berbuah.response.FruitResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel(private val application: Application) : ViewModel() {

    private val listFruitMutable = MutableLiveData<ArrayList<FruitItem>>()
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    internal fun setListFruit(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getSearchFruit(query)
        client.enqueue(object : Callback<FruitResponse> {

            override fun onResponse(
                call: Call<FruitResponse>,
                response: Response<FruitResponse>
            ) {
                _isLoading.value = false
                val listFruit = ArrayList<FruitItem>()
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        response.body()?.artikel?.forEach { fruit ->
                            listFruit.add(
                                FruitItem(
                                    fruit.nama,
                                    fruit.deskripsi,
                                    fruit.image
                                )
                            )
                        }
                        listFruitMutable.postValue(listFruit)
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

    internal fun getFruitSearch(): LiveData<ArrayList<FruitItem>> = listFruitMutable

    companion object {
        private const val TAG = "SearchViewModel"
    }
}