package com.bangkit.berbuah.api

import com.bangkit.berbuah.model.Detail
import com.bangkit.berbuah.model.Search
import com.bangkit.berbuah.response.FruitResponse
import com.bangkit.berbuah.ui.login.LoginResponse
import com.bangkit.berbuah.response.SignupResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun signup(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<SignupResponse>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("search")
    fun getSearchFruit(
        @Query("buah") query: String
    ): Call<Search>

    @GET("buah/{nama}")
    fun getDetailFruit(
        @Path("nama") nama: String
    ): Call<Detail>
}