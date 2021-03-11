package com.example.tp1_cm.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.tp1_cm.dao.NotasDao
import com.example.tp1_cm.entities.Notas
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.security.AccessControlContext

@Database(entities = arrayOf(Notas::class), version = 4, exportSchema = false)
public abstract class NotasDB : RoomDatabase() {

    abstract fun notasDao(): NotasDao

    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var notasDao = database.notasDao()

                    // Delete all content here.
                   //notasDao.deleteAll()

                    // Add sample cities.
                   /* var notas = Notas(1, "Rua das Laurindas", "Cuidado com os acidentes a partir das 18h")
                    notasDao.insert(notas)
                    notas = Notas(2, "Avenida da Liberdade", "Passadeira do topo muito escorregadia")
                    notasDao.insert(notas)
                    notas = Notas(3, "Ponte velha", "Cuidado com os buracos")
                    notasDao.insert(notas)*/

                }
            }
        }
    }


    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: NotasDB? = null

        fun getDatabase(context: Context, scope: CoroutineScope): NotasDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotasDB::class.java,
                    "notas_database"
                )
                    //estratégia de destruição
                    //.fallbackToDestructiveMigration()
                    .addCallback(WordDatabaseCallback(scope))
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}