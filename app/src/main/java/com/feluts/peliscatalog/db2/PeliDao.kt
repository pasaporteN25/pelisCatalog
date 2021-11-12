package com.feluts.peliscatalog.db2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.feluts.peliscatalog.model.PeliculaEnt

@Dao
interface PeliDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addPeli(peli: PeliculaEnt)

    @Query("SELECT * FROM pelis_cache ORDER BY rating DESC")
    suspend fun getAllPelis(): List<PeliculaEnt>

    @Query("SELECT * FROM pelis_cache WHERE id = :id")
    fun getPeliById(id: Int): PeliculaEnt

}