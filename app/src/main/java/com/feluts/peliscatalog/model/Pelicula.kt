package com.feluts.peliscatalog.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Pelicula(@SerializedName("id") val id:Int,
                    @SerializedName("title") val titulo:String,
                    @SerializedName("genre_ids") val genero:ArrayList<Int>,
                    @SerializedName("vote_average") val rating:Double,
                    @SerializedName("poster_path") val img:String):Serializable

