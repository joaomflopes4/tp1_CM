package com.example.tp1_cm

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.tp1_cm.entities.Notas
import com.example.tp1_cm.viewModel.NotasViewModel
import ipvc.estg.cmprojeto.adapters.DESCRICAO
import ipvc.estg.cmprojeto.adapters.ID
import ipvc.estg.cmprojeto.adapters.TITULO

class EditNotasActivity : AppCompatActivity() {
    private lateinit var descricao: EditText
    private lateinit var titulo: EditText
    private lateinit var notasViewModel: NotasViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_notas)

        val editTitulo = intent.getStringExtra(TITULO)
        val editDescricao = intent.getStringExtra(DESCRICAO)

        findViewById<EditText>(R.id.updateTitle).setText(editTitulo)
        findViewById<EditText>(R.id.updateDescription).setText(editDescricao)

        notasViewModel = ViewModelProvider(this).get(NotasViewModel::class.java)
    }

    fun updateNote(view: View) {
        titulo = findViewById(R.id.updateTitle)
        descricao = findViewById(R.id.updateDescription)


        var message = intent.getIntExtra(ID, 0)
        val replyIntent = Intent()
        if (TextUtils.isEmpty(titulo.text) || TextUtils.isEmpty(descricao.text))  {
            setResult(Activity.RESULT_CANCELED, replyIntent)

        } else {
            val nota = Notas(id = message, titulo = titulo.text.toString(), descricao = descricao.text.toString())
            notasViewModel.editNote(nota)


        }
        finish()
    }


    fun cancelUpdate(view: View) {
        var intent = Intent(this, NotasActivity::class.java)
        startActivity(intent)
    }
}