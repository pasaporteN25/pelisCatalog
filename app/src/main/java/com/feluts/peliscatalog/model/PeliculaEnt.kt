package com.feluts.peliscatalog.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "pelis_cache")
data class PeliculaEnt(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val titulo:String,
    val genero: String,
    val idioma: String,
    val rating: Double,
    val img:String
)

class generoTypesConverter{
    @TypeConverter
    fun fromArrayList(list: ArrayList<Int>):String{
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromString(value: String?): ArrayList<Int>{
        val lista = object :TypeToken<ArrayList<Int>>(){}.type
        return Gson().fromJson(value,lista)
    }
}