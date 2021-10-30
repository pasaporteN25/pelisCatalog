package com.feluts.peliscatalog.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pelis_cache")
data class PeliculaEnt(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val titulo:String,
    val genero: ArrayList<Int>,
    val idioma: String,
    val rating: Double,
    val img:String
)