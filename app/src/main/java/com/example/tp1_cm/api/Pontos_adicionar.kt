package com.example.tp1_cm.api

class Pontos_adicionar {
    data class Pontos_adicionar (
            val  latitude: String,
            val longitude: String,
            val nome: String,
            val descricao: String,
            val foto: String,
            val id_user: Int,
            val id_ocorrencia: Int
    )
}