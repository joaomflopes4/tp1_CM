package com.example.tp1_cm.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tp1_cm.entities.Notas

@Dao
interface NotasDao {
    //Get Notas
    @Query(value = "SELECT * from notas_table ORDER BY id DESC")
    fun getAllNotas(): LiveData<List<Notas>>

    //Get Notas pelo Id
    @Query(value = "SELECT * FROM notas_table WHERE id = :id")
    fun getNotasId(id: Int): LiveData<Notas>

    //Insert Notas
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(notas: Notas)

    //delete Notas
    @Query(value = "DELETE FROM notas_table")
    suspend fun deleteAll()

    //Update Notas
    @Update
    suspend fun editNote(notas : Notas)

    //Delete pelo id
    @Query("DELETE FROM notas_table where id ==:id")
    suspend fun deleteNoteById(id: Int?)

/*
    @Query(value = "SELECT * from notas_table WHERE notas == :notas")
    suspend fun deleteByNotas(notas: String)

    //update Notas
    @Update
    suspend fun updateNotas(notas: Notas)

    @Query(value = "UPDATE notas_table SET descricao=:descricao WHERE titulo == :titulo")
    suspend fun updateDescricaoFromId(titulo: String, descricao: String)
*/
}