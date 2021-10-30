package com.feluts.peliscatalog.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.feluts.peliscatalog.model.PeliculaEnt

@Dao
interface PeliDao {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPeli(peli: PeliculaEnt)

    @Query("SELECT * FROM pelis_cache")
    fun getAllPelis(): LiveData<ArrayList<PeliculaEnt>>
}