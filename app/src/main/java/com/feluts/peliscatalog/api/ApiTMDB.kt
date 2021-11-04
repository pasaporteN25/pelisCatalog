package com.feluts.peliscatalog.api

import com.feluts.peliscatalog.model.PeliculaById
import com.feluts.peliscatalog.model.Respuesta
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiTMDB {

    @GET("movie/top_rated")
    fun getMorePeliculas(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = "2f2c5419a59e275f372ef56e8f0ff35b"
    ): Call<Respuesta>

    @GET("movie/{id}")
    fun getPeliculaById(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = "2f2c5419a59e275f372ef56e8f0ff35b"
    ):Call<PeliculaById>

    @GET("search/movie")
    fun search(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = "2f2c5419a59e275f372ef56e8f0ff35b"
    ):Call<Respuesta>

    //Codigos ISO 639-1
    //https://api.themoviedb.org/3/configuration/languages?api_key=2f2c5419a59e275f372ef56e8f0ff35b
}