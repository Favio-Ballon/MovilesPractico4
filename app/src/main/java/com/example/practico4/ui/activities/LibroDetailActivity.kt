package com.example.practico4.ui.activities

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
import com.example.practico4.databinding.ActivityMainBinding
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

        id = intent.getIntExtra("categoriaId", -1)
        if (id != -1) {
            model.loadCategory(id)
        }
        setupViewModelObservers()

    }

    private fun setupViewModelObservers() {
        model.libro.observe(this) {
            if (it == null) {
                return@observe
            }
            binding.lblLibroTitulo.text = it.nombre
            binding.lblLibroDetailAutor.text = it.autor
            binding.lblLibroDetailISBN.text = it.ISBN
            binding.lblLibroDetailRating.text = it.calificacion.toString()
            binding.lblLibroDetailSinopsis.text = it.sinopsis
            Log.d("LibroDetailActivity", it.imagen)
            Glide.with(this)
                .load(it.imagen)
                .into(binding.imagenLibro)
        }
    }
}