package com.example.practico4.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practico4.R
import com.example.practico4.databinding.ActivityGeneroBinding
import com.example.practico4.models.Genero
import com.example.practico4.ui.adapters.GeneroAdapter
import com.example.practico4.ui.adapters.LibroAdapter
import com.example.practico4.ui.viewmodels.GeneroViewModel

class GeneroActivity : AppCompatActivity(), GeneroAdapter.OnGeneroClickListener {
    lateinit var binding : ActivityGeneroBinding
    private val model: GeneroViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityGeneroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupRecyclerView()
        setupViewModelListeners()
        setupEventListeners()
    }

    private fun setupViewModelListeners() {
        model.generosList.observe(this) {
            val adapter = (binding.lstGeneros.adapter as GeneroAdapter)
            adapter.updateData(it)
        }
    }

    override fun onResume() {
        super.onResume()
        model.fetchListaGeneros()
    }

    private fun setupEventListeners() {
        binding.btnAnadirGenero.setOnClickListener {
            val intent = Intent(this, GeneroSaveActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        binding.lstGeneros.apply {
            this.adapter = GeneroAdapter(ArrayList(), this@GeneroActivity)
            layoutManager = LinearLayoutManager(this@GeneroActivity)
        }
    }

    override fun onGeneroEdit(genero: Genero) {
        val intent = Intent(this, GeneroSaveActivity::class.java)
        intent.putExtra("generoId", genero.id)
        startActivity(intent)
    }

    override fun onGeneroDelete(genero: Genero) {
        model.deleteGenero(genero.id!!)
    }

    override fun onGeneroClick(genero: Genero) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("generoId", genero.id)
        startActivity(intent)
    }

}