package com.example.tp1_cm.api

data class OutputPost(
        //recebe resposta do ws - login
        val id: Int,
        val email: String,
        val password: String
)