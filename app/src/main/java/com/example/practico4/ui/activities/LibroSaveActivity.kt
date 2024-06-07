package com.example.practico4.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practico4.R
import com.example.practico4.databinding.ActivityLibroSaveBinding
import com.example.practico4.ui.viewmodels.LibroSaveViewModel

class LibroSaveActivity : AppCompatActivity() {
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

        setupEventListeners()
        setupViewModelObservers()
    }


    private fun setupEventListeners() {
        binding.btnSaveLibro.setOnClickListener {
            model.saveLibro(
                binding.txtTitulo.editText?.text.toString(),
                binding.txtAutor.editText?.text.toString(),
                binding.txtEditorial.editText?.text.toString(),
                binding.txtIsbn.editText?.text.toString(),
                binding.addImagen.editText?.text.toString(),
                binding.txtSinopsis.editText?.text.toString(),
                binding.txtCalificacion.editText?.text.toString().toInt()
            )
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