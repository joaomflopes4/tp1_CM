package com.example.tp1_cm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun paginaNotas(view: View) {
        var intent = Intent(this, NotasActivity::class.java)
        startActivity(intent)
    }
}