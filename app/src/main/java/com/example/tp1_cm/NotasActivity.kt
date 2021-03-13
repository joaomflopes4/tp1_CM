package com.example.tp1_cm

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp1_cm.entities.Notas
import com.example.tp1_cm.viewModel.NotasViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ipvc.estg.cmprojeto.adapters.NotasAdapter

class NotasActivity : AppCompatActivity() {

    private lateinit var notasViewModel: NotasViewModel
    private val newWordActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notas)

        // recycler view
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = NotasAdapter(this, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // view model
        notasViewModel = ViewModelProvider(this).get(NotasViewModel::class.java)
        notasViewModel.allNotas.observe(this, Observer { notas ->
            // Update the cached copy of the words in the adapter.
            notas?.let { adapter.setNotas(it) }
        })

        //Fab
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@NotasActivity, AddNotasActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            val ptitle = data?.getStringExtra(AddNotasActivity.EXTRA_REPLY_TITULO)
            val pdesc = data?.getStringExtra(AddNotasActivity.EXTRA_REPLY_DESCRICAO)

            if (ptitle != null && pdesc != null) {
                val city = Notas(titulo = ptitle, descricao = pdesc)
                notasViewModel.insert(city)
            }

        } else {
            Toast.makeText(
                applicationContext,
                R.string.boxempty,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun updateNote(view: View) {

        var intent = Intent(this, EditNotasActivity::class.java).apply {}
        startActivity(intent)
    }

    fun delete(id: Int?) {
        notasViewModel.deleteNoteById(id)

    }
}