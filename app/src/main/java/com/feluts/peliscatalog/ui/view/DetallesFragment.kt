package com.feluts.peliscatalog.ui.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.feluts.peliscatalog.R
import com.feluts.peliscatalog.databinding.DetallesFragmentBinding
import com.feluts.peliscatalog.ui.viewmodel.DetallesViewModel
import org.w3c.dom.Text

class DetallesFragment : Fragment(R.layout.detalles_fragment) {

    companion object {
        fun newInstance() = DetallesFragment()
    }

    private lateinit var DetallesVM: DetallesViewModel
    private var _binding: DetallesFragmentBinding? = null
    private val binding get() = _binding!!
    val args: DetallesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = DetallesFragmentBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).supportActionBar?.setTitle("Inicio")
        binding.peliConst.visibility = View.INVISIBLE
        binding.progressBar3.visibility = View.VISIBLE
        Handler().postDelayed({
            binding.progressBar3.visibility=View.INVISIBLE
            binding.peliConst.visibility = View.VISIBLE
                              },3000)

        binding.postPuntaje.setOnClickListener(
            View.OnClickListener {
                Toast.makeText(this.context,binding.puntajeTxt.text.toString(),Toast.LENGTH_SHORT).show()

                //esta data pasa al VM y de ahi se hace el post
                //el toast seria si salio bien o no
                //estaria bueno que todx sea una ventana emergente mas que en el mismo fragment
            }
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DetallesVM = ViewModelProvider(this).get(DetallesViewModel::class.java)
        val dataExtra = DetallesVM.getExtraInfo(args.dataPeli.id)
        Log.e("Data","$dataExtra")
        binding.tituloTxt.setText(args.dataPeli.titulo)
        binding.rateTxt.setText(args.dataPeli.rating.toString())
        binding.generoTxt.setText(args.dataPeli.genero.toString())
        Glide.with(requireView().context).load("https://image.tmdb.org/t/p/w500${args.dataPeli.img}")
            .centerCrop()
            .into(binding.poster)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        DetallesVM = ViewModelProvider(this).get(DetallesViewModel::class.java)
        val dataExtra = DetallesVM.getExtraInfo(args.dataPeli.id)
        Handler().postDelayed({
            binding.descripcion.setText(dataExtra[0].resumen)
            binding.estrenoTxt.setText("Estreno: "+dataExtra[0].estreno)
            binding.totalvotesTxt.setText("Votos total: "+dataExtra[0].totalVotos)
                              },3000)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}