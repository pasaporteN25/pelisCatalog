package com.feluts.peliscatalog.ui.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.feluts.peliscatalog.R
import com.feluts.peliscatalog.model.Pelicula
import com.feluts.peliscatalog.rv.PeliculaAdapter
import com.feluts.peliscatalog.ui.viewmodel.InicioViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class InicioFragment : Fragment() {

    companion object {
        fun newInstance() = InicioFragment()
    }

    private lateinit var InicioVM: InicioViewModel
    private lateinit var rvPelis: RecyclerView
    private lateinit var PBar: ProgressBar
    private lateinit var cont: ConstraintLayout
    var paginAct:Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.inicio_fragment, container, false)
        (activity as AppCompatActivity).supportActionBar?.show()

        InicioVM = ViewModelProvider(this).get(InicioViewModel::class.java)
        rvPelis = view.findViewById(R.id.peli_rv)
        rvPelis.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        PBar = view.findViewById(R.id.progressBar)
        cont = view.findViewById(R.id.contenedor_inicio)
        rvPelis.visibility = View.INVISIBLE
        rvPelis.isVisible = false


        lanzadera()


        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rvPelis.addOnScrollListener(object :  RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if(!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)){
                    Toast.makeText(context,"Final",Toast.LENGTH_LONG).show()

                    lanzadera2(paginAct)
                }
            }

        })
    }

    fun lanzadera(){
        GlobalScope.launch(Dispatchers.IO) {

            try {
                val info = InicioVM.getAllMovies()


                withContext(Dispatchers.Main) {
                    PBar.isVisible = false
                    PBar.visibility = View.INVISIBLE
                    rvPelis.isVisible = true
                    rvPelis.visibility = View.VISIBLE

                    rvPelis.adapter = PeliculaAdapter(info)
                    Handler().postDelayed({ rvPelis.adapter?.notifyDataSetChanged() }, 3000)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Revisa tu conexion a internet", Toast.LENGTH_SHORT)
                        .show()
                    cont.setOnClickListener(
                        View.OnClickListener {
                            lanzadera()
                        }
                    )
                }
            }
        }
    }

    fun lanzadera2(page: Int){
        GlobalScope.launch(Dispatchers.IO) {

            try {
                val info = InicioVM.getMoreTRM(page)


                withContext(Dispatchers.Main) {
                    PBar.isVisible = false
                    PBar.visibility = View.INVISIBLE
                    rvPelis.isVisible = true
                    rvPelis.visibility = View.VISIBLE

                    rvPelis.adapter = PeliculaAdapter(info)
                    Handler().postDelayed({ rvPelis.adapter?.notifyDataSetChanged() }, 3000)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Revisa tu conexion a internet", Toast.LENGTH_SHORT)
                        .show()
                    lanzadera2(page)
                    cont.setOnClickListener(
                        View.OnClickListener {
                            lanzadera2(page)
                        }
                    )
                }
            }
        }
    }


}

