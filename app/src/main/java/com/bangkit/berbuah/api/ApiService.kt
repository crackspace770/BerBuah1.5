package com.bangkit.berbuah.api

import com.bangkit.berbuah.model.Detail
import com.bangkit.berbuah.model.Search
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("artikel")
    fun getSearchFruit(
        @Query("nama") query: String
    ): Call<Search>

    @GET("artikel/{nama}")
    fun getDetailFruit(
        @Path("nama") nama: String
    ): Call<Detail>
}