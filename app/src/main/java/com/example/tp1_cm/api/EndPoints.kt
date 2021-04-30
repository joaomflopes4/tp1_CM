package com.example.tp1_cm.api

import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.*

interface EndPoints { //Faz os pedidos ao ws

    @GET("myslim/api/utilizador")
    fun getUsers(): Call<List<User>>

    @GET("/utilizador/{id}")
    fun getUserById(@Path("id") id: Int): Call<User>

    //post -> email e password | login ws
    @FormUrlEncoded
    @POST("/myslim/api/utilizador")
    fun postTest(@Field("email") first: String, @Field ("password") second: String): Call<OutputPost>

    //Get -> Pontos
    @GET("/myslim/api/pontos")
    fun getPontos(): Call<List<Pontos>>

    //Editar pontos
    @FormUrlEncoded
    @POST("/myslim/api/editar/pontos")
    fun editarOcorrencia(@Field("id") first: Int, @Field ("nome") second: String, @Field ("descricao") third: String): Call<EditarOcorrencias>
}