package com.example.tp1_cm.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.tp1_cm.db.NotasDB
import com.example.tp1_cm.db.NotasRepository
import com.example.tp1_cm.entities.Notas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotasViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NotasRepository
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allNotas: LiveData<List<Notas>>

    init {
        val notasDao = NotasDB.getDatabase(application, viewModelScope).notasDao()
        repository = NotasRepository(notasDao)
        allNotas = repository.allNotas
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(notas: Notas) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(notas)
    }

    fun editNote(nota: Notas) = viewModelScope.launch {
        repository.editNote(nota)
    }

    fun deleteNoteById(id: Int?) = viewModelScope.launch {
        repository.deleteNoteById(id)

    }
}