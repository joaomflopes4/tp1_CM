package com.example.tp1_cm.api

import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.*

interface EndPoints {
    @GET("myslim/api/utilizador")
    fun getUsers(): Call<List<User>>

    @GET("/utilizador/{id}")
    fun getUserById(@Path("id") id: Int): Call<User>

    @FormUrlEncoded
    @POST("/myslim/api/utilizador")
    fun postTest(@Field("email") first: String, @Field ("password") second: String): Call<OutputPost>
}