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

    //Editar ponto
    @FormUrlEncoded
    @POST("/myslim/api/editar/pontos")
    fun editarOcorrencia(@Field("id") first: Int, @Field ("nome") second: String, @Field ("descricao") third: String): Call<EditarOcorrencias>

    //Eliminar ponto
    @FormUrlEncoded
    @POST("/myslim/api/pontos/delete/ponto")
    fun eliminarOcorrencia(@Field("id") first: Int): Call<EliminarOcorrencias>

    //Adicionar Pontos
    @FormUrlEncoded
    @POST("/myslim/api/pontos")
    fun adicionarOcorrencia(@Field("latitude") primeiro: String,
                            @Field("longitude") segundo: String,
                            @Field("nome") terceiro: String,
                            @Field("descricao") quarto: String,
                            @Field("foto") quinto: String,
                            @Field("id_user") sexto: Int,
                            @Field("id_ocorrencia") setimo: Int,
                            @Field("imagem") oitavo:String,
                            @Field("nomeImagem") nono:String): Call<Pontos_adicionar>
}