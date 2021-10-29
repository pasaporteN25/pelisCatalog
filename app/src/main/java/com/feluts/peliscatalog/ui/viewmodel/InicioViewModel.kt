package com.feluts.peliscatalog.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.feluts.peliscatalog.api.ApiTMDB
import com.feluts.peliscatalog.api.ApiTMDBImp
import com.feluts.peliscatalog.model.Pelicula
import com.feluts.peliscatalog.model.Respuesta
import com.feluts.peliscatalog.rv.PeliculaAdapter
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse

class InicioViewModel : ViewModel() {

    private val listaPeliculas = ArrayList<Pelicula>()

    suspend fun getTopRatedMovies(): Call<Respuesta>{
        val api = ApiTMDBImp()

        return api.getTopRatedMovies()
    }

    suspend fun getAllMovies(): ArrayList<Pelicula>{

        val resp = getTopRatedMovies().awaitResponse()
        if(resp.isSuccessful){
            val data= resp.body()
            if (data != null){
                var total:Int=0
                for(peli in data.peliculas){
                    listaPeliculas.add(
                        Pelicula(peli.id
                            ,peli.titulo
                            ,peli.genero
                            ,peli.rating
                            ,peli.img)
                    )
                    total+=1
                }
                Log.e("Cargando peliculas... ","$total")
        }
//        resp.enqueue(
//            object : Callback<Respuesta>{
//                override fun onFailure(call: Call<Respuesta>, t: Throwable) {
//                    Log.e("Error al invocar la API", t.message.toString())
//                }
//
//                override fun onResponse(call: Call<Respuesta>, response: Response<Respuesta>) {
//                    val data= response.body()
//                    if (data != null){
//                        var total:Int=0
//                        for(peli in data.peliculas){
//                            listaPeliculas.add(
//                                Pelicula(peli.id
//                                ,peli.titulo
//                                ,peli.genero
//                                ,peli.rating
//                                ,peli.img)
//                            )
//                            total+=1
//                        }
//                        Log.e("Cargando peliculas... ","$total")
//                    }
//
//                }
//            }
//        )
        //return  listaPeliculas
    }
return listaPeliculas}}