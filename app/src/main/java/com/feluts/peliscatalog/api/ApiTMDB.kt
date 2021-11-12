package com.feluts.peliscatalog.api

import com.feluts.peliscatalog.model.PeliculaById
import com.feluts.peliscatalog.model.PostResp
import com.feluts.peliscatalog.model.Respuesta
import retrofit2.Call
import retrofit2.http.*

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

    @POST("movie/{id}/rating")
    @Headers("application/json;charset=utf-8")
    fun puntuar(
        @Path("id") id: Int,
        @Body value: Double,
        @Query("api_key") apiKey: String = "2f2c5419a59e275f372ef56e8f0ff35b",

    ):Call<PostResp>

    //Codigos ISO 639-1
    //https://api.themoviedb.org/3/configuration/languages?api_key=2f2c5419a59e275f372ef56e8f0ff35b
}