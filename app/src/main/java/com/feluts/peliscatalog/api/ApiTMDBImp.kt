package com.feluts.peliscatalog.api

import com.feluts.peliscatalog.model.Pelicula
import com.feluts.peliscatalog.model.PeliculaById
import com.feluts.peliscatalog.model.Respuesta
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class ApiTMDBImp {

    private fun getRetrofit(): Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.themoviedb.org/3/").build()
    }

//    fun getTopRatedMovies(): Call<Respuesta>{
//        return getRetrofit().create(ApiTMDB::class.java).getPelicula()
//    }

    fun getMoreTopRated(page:Int):Call<Respuesta>{
        return getRetrofit().create(ApiTMDB::class.java).getMorePeliculas(page)
    }

    fun getMovieById(id: Int): Call<PeliculaById>{
        return getRetrofit().create(ApiTMDB::class.java).getPeliculaById(id)
    }

    //aca iria para traer los datos de la peli elegida.
}