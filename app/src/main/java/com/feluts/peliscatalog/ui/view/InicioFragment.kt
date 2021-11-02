package com.feluts.peliscatalog.ui.view

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.feluts.peliscatalog.R
import com.feluts.peliscatalog.databinding.InicioFragmentBinding
import com.feluts.peliscatalog.model.Pelicula
import com.feluts.peliscatalog.model.PeliculaEnt
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

    private var _binding: InicioFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var InicioVM: InicioViewModel
    private lateinit var cont: ConstraintLayout
    var paginAct: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //val view = inflater.inflate(R.layout.inicio_fragment, container, false)
        _binding = InicioFragmentBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).supportActionBar?.show()
        InicioVM = ViewModelProvider(this).get(InicioViewModel::class.java)
        val rvPelis = binding.peliRv
        val PBar = binding.progressBar

        //Se supone que esto ayudaba a que no suba cuando lo recargo...
        rvPelis.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        rvPelis.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        rvPelis.visibility = View.INVISIBLE
        rvPelis.isVisible = false

        //Probar si existe la info en la db sino aregarla,
        //si existe traerla

        if (isOnline(requireContext()) == true) {

            lanzadera(1)

        } else {
            Toast.makeText(requireContext(), "Revisa la conexion a internet", Toast.LENGTH_LONG)
                .show()

            try {

                PBar.isVisible = false
                PBar.visibility = View.INVISIBLE
                rvPelis.isVisible = true
                rvPelis.visibility = View.VISIBLE

                GlobalScope.launch(Dispatchers.IO){
                    val pelisdb = InicioVM.getPelis()

                    val gen: ArrayList<Int> = arrayListOf<Int>()
                    var pelis: ArrayList<Pelicula> = arrayListOf<Pelicula>()
                    for (p in pelisdb) {
                        gen.add(0)
                        pelis.add(Pelicula(p.id, p.titulo, gen, p.idioma, p.rating, p.img))
                        Log.e("DB load:", p.titulo)
                    }

                    withContext(Dispatchers.Main){
                        rvPelis.adapter = PeliculaAdapter(pelis)
                        Handler().postDelayed({ rvPelis.adapter?.notifyDataSetChanged() }, 3000)
                    }

                }

            } catch (e: Exception) {
                Log.d("Error al traer info", e.toString())
            }
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val rvPelis = binding.peliRv
        rvPelis.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_UP)) {
                    if(!isOnline(requireContext().applicationContext)){
                        Toast.makeText(requireContext(),"No hay internet",Toast.LENGTH_LONG).show()
                    }else{
                        //si no hay internet deberia saber hasta que pagina tengo guardado
                        //en la
                        //en las pruebas cargo raro
                        paginAct += 1
                        lanzadera(paginAct)
                        Log.d("Api:", "Cargando mas...")
                    }
                }
            }
        })




    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //val recyclerViewState = rvPelis.layoutManager?.onSaveInstanceState()

        //rvPelis.layoutManager?.onRestoreInstanceState(recyclerViewState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun lanzadera(page: Int) {
        GlobalScope.launch(Dispatchers.IO) {

            try {
                val info = InicioVM.getMoreTRM(page)
                info.forEachIndexed { index, p ->
                    val peli = PeliculaEnt(
                        info[index].id, info[index].titulo, "otro",
                        info[index].idioma, info[index].rating, info[index].img
                    )
                    InicioVM.addPeli(peli)
                }


                //log de guardado en db
                //Log.d("DB: ","Punto de guardado")

                withContext(Dispatchers.Main) {

                    if (page == 1) {
                        binding.progressBar.isVisible = false
                        binding.progressBar.visibility = View.INVISIBLE
                        binding.peliRv.isVisible = true
                        binding.peliRv.visibility = View.VISIBLE
                    }

                    binding.peliRv.adapter = PeliculaAdapter(info)
                    Handler().postDelayed({ binding.peliRv.adapter?.notifyDataSetChanged() }, 3000)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {

                    Toast.makeText(requireContext(), "No se pudo cargar mas", Toast.LENGTH_SHORT)
                        .show()

                    lanzadera(page)
                    cont.setOnClickListener(
                        View.OnClickListener {
                            lanzadera(page)
                        }
                    )
                }
            }
        }
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }

        return false
    }

}

