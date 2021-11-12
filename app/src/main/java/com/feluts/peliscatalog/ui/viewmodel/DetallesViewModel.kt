package com.feluts.peliscatalog.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.feluts.peliscatalog.api.ApiTMDBImp
import com.feluts.peliscatalog.model.PeliculaById
import com.feluts.peliscatalog.model.PostResp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetallesViewModel : ViewModel() {

    private val datosPelicula = ArrayList<PeliculaById>()

    fun getInfoById(id: Int): Call<PeliculaById>{

        return ApiTMDBImp().getMovieById(id)
    }

    fun puntuar(id: Int, puntos: Double): Call<PostResp>{
        return ApiTMDBImp().puntuar(id,puntos)
    }

    fun getExtraInfo(id: Int): ArrayList<PeliculaById>{
        getInfoById(id).enqueue(
            object : Callback<PeliculaById>{
                override fun onFailure(call: Call<PeliculaById>, t: Throwable) {
                    Log.e("Error al traer el id", t.message.toString())
                }

                override fun onResponse(
                    call: Call<PeliculaById>,
                    response: Response<PeliculaById>
                ) {
                    val data = response.body()
                    if (data != null){
                        Log.e("datos en vm:", "Cargando...")
                        datosPelicula.add(PeliculaById(data.resumen,data.estreno,data.totalVotos))
                    }
                }
            }
        )
        return datosPelicula
    }
}