package com.bangkit.berbuah.api

import com.bangkit.berbuah.response.FruitResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("artikel/search")
    fun getSearchFruit(
        @Query("buah") query: String
    ): Call<FruitResponse>

    @GET("artikel/nama")
    fun getDetailFruit(
        @Query("buah") nama: String
    ): Call<FruitResponse>
}