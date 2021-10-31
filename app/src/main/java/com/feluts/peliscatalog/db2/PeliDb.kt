package com.feluts.peliscatalog.db2

import androidx.room.Database
import androidx.room.RoomDatabase
import com.feluts.peliscatalog.model.PeliculaEnt

@Database(
    entities = [PeliculaEnt::class],
    version = 1
)
abstract class PeliDb: RoomDatabase() {

    abstract fun peliDao():PeliDao
}