package com.example.tp1_cm.db

import androidx.lifecycle.LiveData
import com.example.tp1_cm.dao.NotasDao
import com.example.tp1_cm.entities.Notas

class NotasRepository(private val notasDao: NotasDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allNotas: LiveData<List<Notas>> = notasDao.getAllNotas()

    suspend fun insert(notas: Notas) {
        notasDao.insert(notas)
    }

    //Editar notas
    suspend fun editNote(notas: Notas){
        notasDao.editNote(notas)
    }

    //Eliminar notas
    suspend fun deleteNoteById(id:Int?){
        notasDao.deleteNoteById(id)
    }
/*
    suspend fun deleteAll(){
        notasDao.deleteAll()
    }

    suspend fun deleteByCity(notas: String){
        notasDao.deleteByNotas(notas)
    }

    suspend fun updateCity(notas: Notas) {
        notasDao.updateNotas(notas)
    }

    suspend fun updateDescricaoFromId(city: String, country: String){
        notasDao.updateDescricaoFromId(city, country)
    }*/
}