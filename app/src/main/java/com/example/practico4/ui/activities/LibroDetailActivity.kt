package com.example.practico4.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.practico4.R
import com.example.practico4.databinding.ActivityLibroDetailBinding
import com.example.practico4.ui.viewmodels.LibroDetailViewModel

class LibroDetailActivity : AppCompatActivity() {
    private var id: Int = 0
    lateinit var binding: ActivityLibroDetailBinding
    private val model: LibroDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLibroDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainLibro)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        id = intent.getIntExtra("libroId", -1)
        if (id != -1) {
            model.loadCategory(id)
        }
        setupViewModelObservers()
        setupEventListeners()
    }

    override fun onResume() {
        super.onResume()
        model.loadCategory(id)
    }

    private fun setupViewModelObservers() {
        model.libro.observe(this) {
            if (it == null) {
                return@observe
            }
            binding.lblLibroTitulo.text = it.nombre
            binding.lblLibroDetailAutor.text = it.autor
            binding.lblLibroDetailISBN.text = it.isbn
            binding.lblLibroDetailRating.text = it.calificacion.toString()
            binding.lblLibroDetailSinopsis.text = it.sinopsis
            binding.lblLibroEditorial.text = it.editorial

            var temp = ""
            for (genero in it.generos) {
                if (genero == it.generos.last()) {
                    temp += genero.nombre
                }else {
                    temp += genero.nombre + ", "
                }
            }
            binding.lblLibroGeneros.text = temp
            Log.d("LibroDetailActivity", it.generos.toString())
            Glide.with(this)
                .load(it.imagen)
                .into(binding.imagenLibro)
        }
        model.closeActivity.observe(this) {
            if (it) {
                finish()

            }
        }
    }

    private fun setupEventListeners() {

        binding.btnEditar.setOnClickListener {
                val intent = Intent(this, LibroSaveActivity::class.java)
                intent.putExtra("libroId", id)
                startActivity(intent)
        }

        binding.btnBorrar.setOnClickListener {
            model.deleteLibro(id)
        }

    }


}