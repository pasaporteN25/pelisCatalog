package com.feluts.peliscatalog.api

import com.feluts.peliscatalog.model.Pelicula
import com.feluts.peliscatalog.model.PeliculaById
import com.feluts.peliscatalog.model.Respuesta
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiTMDB {

    @GET("movie/top_rated")
    fun getPelicula(
        @Query("api_key") apiKey: String = "2f2c5419a59e275f372ef56e8f0ff35b"
    ):Call<Respuesta>

    @GET("movie/{id}")
    fun getPeliculaById(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = "2f2c5419a59e275f372ef56e8f0ff35b"
    ):Call<PeliculaById>
}