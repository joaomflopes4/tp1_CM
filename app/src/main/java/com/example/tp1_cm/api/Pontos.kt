package com.example.tp1_cm.api

data class Pontos (
        //recebe resposta do ws - pontos
        val id: Int,
        val  latitude: String,
        val longitude: String,
        val nome: String,
        val descricao: String,
        val foto: String,
        val id_user: Int,
        val id_ocorrencia: Int
)