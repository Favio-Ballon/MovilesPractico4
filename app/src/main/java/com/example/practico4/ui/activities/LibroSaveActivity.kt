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
import com.example.practico4.databinding.ActivityLibroSaveBinding
import com.example.practico4.ui.viewmodels.LibroSaveViewModel

class LibroSaveActivity : AppCompatActivity() {
    private var id: Int = -1
    lateinit var binding: ActivityLibroSaveBinding
    private val model: LibroSaveViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLibroSaveBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        id = intent.getIntExtra("libroId", -1)
        if (id != -1) {
            model.loadCategory(id)
        }

        setupEventListeners()
        setupViewModelObservers()
    }


    private fun setupEventListeners() {
        binding.btnSaveLibro.setOnClickListener {
            model.saveLibro(
                id,
                binding.txtTitulo.editText?.text.toString(),
                binding.txtAutor.editText?.text.toString(),
                binding.txtEditorial.editText?.text.toString(),
                binding.txtIsbn.editText?.text.toString(),
                binding.addImagen.editText?.text.toString(),
                binding.txtSinopsis.editText?.text.toString(),
                binding.txtCalificacion.editText?.text.toString().toInt()
            )
        }

        model.libro.observe(this) {
            if (it == null) {
                return@observe
            }
            binding.txtTitulo.editText?.setText(it.nombre)
            binding.txtAutor.editText?.setText(it.autor)
            binding.txtIsbn.editText?.setText(it.isbn)
            binding.txtEditorial.editText?.setText(it.editorial)
            binding.txtCalificacion.editText?.setText(it.calificacion.toString())
            binding.txtSinopsis.editText?.setText(it.sinopsis)
            binding.addImagen.editText?.setText(it.imagen)
        }
    }

    private fun setupViewModelObservers() {
        model.closeActivity.observe(this) {
            if (it) {
                finish()
            }
        }
    }

}