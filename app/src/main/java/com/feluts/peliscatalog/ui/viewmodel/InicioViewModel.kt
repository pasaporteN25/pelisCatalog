package com.feluts.peliscatalog.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feluts.peliscatalog.api.ApiTMDB
import com.feluts.peliscatalog.api.ApiTMDBImp
import com.feluts.peliscatalog.db2.PeliApp.Companion.db
import com.feluts.peliscatalog.model.Pelicula
import com.feluts.peliscatalog.model.PeliculaEnt
import com.feluts.peliscatalog.model.Respuesta
import com.feluts.peliscatalog.rv.PeliculaAdapter
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse

class InicioViewModel (application: Application): AndroidViewModel(application) {

    val listaPeliculas = ArrayList<Pelicula>()
//    private val repository: PelisRepository
//    private val getAllPelis: List<PeliculaEnt>
//
//
//    init{
//        val peliDao = PelisDatabase.getDB(application).peliDao()
//        repository = PelisRepository(peliDao)
//        getAllPelis = repository.getAllPelis
//    }

    fun getTopRatedMovies(): Call<Respuesta> {
        val api = ApiTMDBImp()

        return api.getTopRatedMovies()
    }


    fun getMoreTopRatedMovies(page: Int): Call<Respuesta> {
        val api = ApiTMDBImp()

        return api.getMoreTopRated(page)
    }

    suspend fun getAllMovies(): ArrayList<Pelicula> {

        val resp = getTopRatedMovies().awaitResponse()
        if (resp.isSuccessful) {
            val data = resp.body()
            if (data != null) {
                var total: Int = 0
                for (peli in data.peliculas) {
                    listaPeliculas.add(
                        Pelicula(
                            peli.id, peli.titulo, peli.genero, peli.idioma, peli.rating, peli.img
                        )
                    )
                    total += 1
                }
                Log.e("Cargando peliculas... ", "$total")
            }
        }
        return listaPeliculas
    }

    suspend fun getMoreTRM(page: Int): ArrayList<Pelicula>{
        val resp = getMoreTopRatedMovies(page).awaitResponse()
        if (resp.isSuccessful) {
            val data = resp.body()
            if (data != null) {
                var total: Int = 0
                for (peli in data.peliculas) {
                    listaPeliculas.add(
                        Pelicula(
                            peli.id, peli.titulo, peli.genero, peli.idioma, peli.rating, peli.img
                        )
                    )
                    total += 1
                }
                Log.e("Cargando peliculas... ", "$total")
            }
        }
        return listaPeliculas

    }

    fun addPeli(peli: PeliculaEnt){

        viewModelScope.launch(Dispatchers.IO) {

            db.peliDao().addPeli(peli)
        }
    }



}