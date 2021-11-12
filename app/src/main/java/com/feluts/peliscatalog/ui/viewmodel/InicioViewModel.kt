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

class InicioViewModel(application: Application) : AndroidViewModel(application) {

    val listaPeliculas = ArrayList<Pelicula>()
    val respBusqueda = ArrayList<Pelicula>()


    fun getMoreTopRatedMovies(page: Int): Call<Respuesta> {
        val api = ApiTMDBImp()
        return api.getMoreTopRated(page)
    }

    fun buscarPelicula(query: String): Call<Respuesta>{
        val api = ApiTMDBImp()
        return api.buscar(query)
    }
    //creo que puedo unificar ambas peticiones a este nivel
    //dependiendo de si tiene query o no tiro un get o el otro

    suspend fun getMoreTRM(page: Int): ArrayList<Pelicula> {
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

    suspend fun buscarPeli(query: String): ArrayList<Pelicula>{
        val resp = buscarPelicula(query).awaitResponse()
        if(resp.isSuccessful){
            val data = resp.body()
            if(data!=null){
                for(peli in data.peliculas){
                    val image:String
                    if(peli.img==null){
                        image=""
                    }else{
                        image=peli.img
                    }
                    respBusqueda.add(Pelicula(peli.id, peli.titulo, peli.genero
                        , peli.idioma, peli.rating, image))
                }
            }
        }else{
            Log.d("InicioVM: ","Error al traer")
        }
        return respBusqueda
    }

    //DB:

    fun addPeli(peli: PeliculaEnt) {

        viewModelScope.launch(Dispatchers.IO) {

            db.peliDao().addPeli(peli)
        }
    }

    suspend fun getPelis(): List<PeliculaEnt> {
        return db.peliDao().getAllPelis()
    }


}