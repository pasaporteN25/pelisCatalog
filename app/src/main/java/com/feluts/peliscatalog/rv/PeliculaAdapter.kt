package com.feluts.peliscatalog.rv

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.feluts.peliscatalog.R
import com.feluts.peliscatalog.databinding.InicioFragmentBinding
import com.feluts.peliscatalog.model.Pelicula
import com.feluts.peliscatalog.ui.view.DetallesFragmentArgs
import com.feluts.peliscatalog.ui.view.InicioFragmentDirections

class PeliculaAdapter(private var listaPelis: ArrayList<Pelicula>): RecyclerView.Adapter<PeliculaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pelicula_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.id.setText(listaPelis[position].id.toString())
        holder.titulo.setText(listaPelis[position].titulo)
        //medidas del poster:
        // w92, w154, w185, w342
        // w500, w780, original
        //
        Glide.with(holder.vista.context)
            .load("https://image.tmdb.org/t/p/w185${listaPelis[position].img}")
            .centerCrop()
            .into(holder.img)
        holder.itemView.setOnClickListener(
            View.OnClickListener {
                holder.vista.findNavController().navigate(
                    //holder.id.text.toString().toInt()

                    InicioFragmentDirections.actionInicioFragmentToDetallesFragment(holder.id.text.toString().toInt(),listaPelis[position])
                )
            }
        )

    }

    override fun getItemCount(): Int {
        return listaPelis.size
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {

        val id:TextView
        val titulo: TextView
        val img:ImageView
        val vista:View


        init {
            id = view.findViewById(R.id.peli_id)
            titulo = view.findViewById(R.id.peli_titulo)
            img = view.findViewById(R.id.peli_img)
            vista=view
        }
    }
}