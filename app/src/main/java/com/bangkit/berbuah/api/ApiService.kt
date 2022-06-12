package com.bangkit.berbuah.api

import com.bangkit.berbuah.model.Detail
import com.bangkit.berbuah.model.Search
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("search")
    fun getSearchFruit(
        @Query("buah") query: String
    ): Call<Search>

    @GET("buah/{nama}")
    fun getDetailFruit(
        @Path("nama") nama: String
    ): Call<Detail>
}