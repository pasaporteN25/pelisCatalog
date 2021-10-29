package com.feluts.peliscatalog.model

import com.google.gson.annotations.SerializedName

data class Respuesta (
    @SerializedName("page") val pagina: Int,
    @SerializedName("results") val peliculas: ArrayList<Pelicula>,
    @SerializedName("total_pages") val totalPaginas: Int,
    @SerializedName("total_results") val totalRes: Int
)