package com.feluts.peliscatalog.model

import com.google.gson.annotations.SerializedName

data class PeliculaById (//@SerializedName("genres[0]") val genero:String,
                         @SerializedName("overview") val resumen:String,
                         @SerializedName("release_date") val estreno:String,
                         @SerializedName("vote_count") val totalVotos:Int
                         )