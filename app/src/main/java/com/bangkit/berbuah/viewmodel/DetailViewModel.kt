package com.bangkit.berbuah.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.berbuah.api.ApiConfig
import com.bangkit.berbuah.model.Detail
import com.bangkit.berbuah.model.DetailFruit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val application: Application) : ViewModel() {

    private val listDetailFruitMutable = MutableLiveData<ArrayList<DetailFruit>>()
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    internal fun setDetailFruit(nama: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailFruit(nama)
        client.enqueue(object : Callback<Detail> {

            override fun onResponse(
                call: Call<Detail>,
                response: Response<Detail>
            ) {
                _isLoading.value = false
                val listDetailFruit = ArrayList<DetailFruit>()
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        response.body()?.data?.forEach { detailFruit ->
                            listDetailFruit.add(
                                DetailFruit(
                                    detailFruit.nama,
                                    detailFruit.nama_latin,
                                    detailFruit.deskripsi,
                                    detailFruit.gambar,
                                    detailFruit.manfaat,
//                                    detailFruit.nutrisi.air
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

            override fun onFailure(call: Call<Detail>, t: Throwable) {
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

    companion object {
        private const val TAG = "DetailViewModel"
    }
}