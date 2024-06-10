package com.example.practico4.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge

import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practico4.R
import com.example.practico4.databinding.ActivityGeneroSaveBinding
import com.example.practico4.ui.viewmodels.GeneroSaveViewModel


class GeneroSaveActivity : AppCompatActivity() {
    lateinit var binding: ActivityGeneroSaveBinding
    private val model : GeneroSaveViewModel by viewModels()
    private var generoId : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityGeneroSaveBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        generoId = intent.getIntExtra("generoId", -1)
        if (generoId != -1) {
            model.loadGenero(generoId)
        }
        setupViewModelObservers()
        setupEventListeners()

    }

    private fun setupViewModelObservers() {
        model.closeActivity.observe(this) {
            if (it) {
                finish()
            }
        }

        model.genero.observe(this) {
            if (it == null) {
                return@observe
            }
            binding.txtGeneroName.editText?.setText(it.nombre)
        }
    }

    private fun setupEventListeners() {
        binding.btnSaveGenero.setOnClickListener {
            model.saveGenero(
                generoId,
                binding.txtGeneroName.editText?.text.toString()
            )
        }
    }
}
