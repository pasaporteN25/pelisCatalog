package com.feluts.peliscatalog.rv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.feluts.peliscatalog.R
import com.feluts.peliscatalog.model.Pelicula
import com.feluts.peliscatalog.ui.view.InicioFragmentDirections

class PeliculaAdapter(private var listaPelis: ArrayList<Pelicula>): RecyclerView.Adapter<PeliculaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pelicula_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titulo.setText(listaPelis[position].titulo)
        holder.idioma.setText(listaPelis[position].idioma)
        //medidas del poster:
        // w92, w154, w185, w342
        // w500, w780, original

        Glide.with(holder.vista.context)
            .load("https://image.tmdb.org/t/p/w185${listaPelis[position].img}")
            .centerCrop()
            .into(holder.img)
        holder.itemView.setOnClickListener(
            View.OnClickListener {
                holder.vista.findNavController().navigate(
                    //holder.id.text.toString().toInt()

                    InicioFragmentDirections.actionInicioFragmentToDetallesFragment(listaPelis[position].id.toString().toInt(),listaPelis[position])
                )
            }
        )

    }

    override fun getItemCount(): Int {
        return listaPelis.size
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {

        val titulo: TextView
        val idioma:TextView
        val img:ImageView
        val vista:View


        init {
            titulo = view.findViewById(R.id.peli_titulo)
            idioma = view.findViewById(R.id.peli_idioma)
            img = view.findViewById(R.id.peli_img)
            vista=view
        }
    }
}