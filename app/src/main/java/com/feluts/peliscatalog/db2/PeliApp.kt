package com.feluts.peliscatalog.db2

import android.app.Application
import androidx.room.Room

class PeliApp: Application() {
    companion object{
        lateinit var db:PeliDb
    }

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(this,PeliDb::class.java,"pelis_cache").build()
    }

}