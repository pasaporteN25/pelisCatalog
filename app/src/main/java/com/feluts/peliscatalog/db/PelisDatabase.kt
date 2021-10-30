package com.feluts.peliscatalog.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.feluts.peliscatalog.model.PeliculaEnt

@Database(entities = [PeliculaEnt::class], version = 1, exportSchema = false)
abstract class PelisDatabase: RoomDatabase() {

    abstract fun peliDao(): PeliDao

    companion object{
        @Volatile
        private var INSTANCE: PelisDatabase? = null

        fun getDB(context: Context): PelisDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PelisDatabase::class.java,
                    "pelis_db"
                ).build()
                INSTANCE= instance
                return instance
            }
        }
    }
}