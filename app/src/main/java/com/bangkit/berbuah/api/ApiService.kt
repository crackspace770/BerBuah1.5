package com.bangkit.berbuah.api

import com.bangkit.berbuah.response.FruitResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("artikel/search")
    fun getSearchFruit(
        @Query("buah") query: String
    ): Call<FruitResponse>

    @GET("artikel")
    fun getDetailFruit(
        @Query("nama") nama: String
    ): Call<FruitResponse>

    @GET("artikel")
    fun getScanFruit(
        @Query("nama") nama: String
    ): Call<FruitResponse>
}