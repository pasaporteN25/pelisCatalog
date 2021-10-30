package com.feluts.peliscatalog.db

import androidx.lifecycle.LiveData
import com.feluts.peliscatalog.model.PeliculaEnt

class PelisRepository( private val peliDao: PeliDao) {

    val getAllPelis: LiveData<ArrayList<PeliculaEnt>> = peliDao.getAllPelis()

    suspend fun addPeli(peli: PeliculaEnt){
        peliDao.addPeli(peli)
    }
}