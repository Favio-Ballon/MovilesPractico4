package com.example.practico4.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practico4.R
import com.example.practico4.databinding.ActivityMainBinding
import com.example.practico4.models.Libro
import com.example.practico4.models.Libros
import com.example.practico4.ui.adapters.LibroAdapter
import com.example.practico4.ui.viewmodels.MainViewModel

class MainActivity : AppCompatActivity(), LibroAdapter.OnLibrosClickListener {
    lateinit var binding: ActivityMainBinding
    private val model: MainViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupRecyclerView()
        setupViewModelListeners()

    }

    override fun onResume() {
        super.onResume()
        model.fetchListaLibros()
    }

    private fun setupViewModelListeners() {
        model.librosList.observe(this) {
            val adapter = (binding.lstLibros.adapter as LibroAdapter)
            adapter.updateData(it)
        }
    }

    private fun setupRecyclerView() {
        binding.lstLibros.apply {
            this.adapter = LibroAdapter(Libros(), this@MainActivity)
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    override fun onLibrosClick(libro: Libro) {
        TODO("Not yet implemented")
    }
}