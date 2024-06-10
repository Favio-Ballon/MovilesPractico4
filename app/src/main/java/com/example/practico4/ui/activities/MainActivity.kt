package com.example.practico4.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
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
    private var id: Int = -1
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
        id = intent.getIntExtra("generoId", -1)

        setupRecyclerView()
        setupViewModelListeners()
        setupEventListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_generos) {
            val intent = Intent(this, GeneroActivity::class.java)
            startActivity(intent)
            return true
        }

        return super.onOptionsItemSelected(item)

    }
    override fun onResume() {
        super.onResume()
        model.fetchListaLibros()
    }

    private fun setupViewModelListeners() {

        model.librosList.observe(this) {libros ->
            val librosList = ArrayList(libros)
            if(id != -1) {
                var sortedLibros = arrayListOf<Libro>()
                for (libro in libros) {
                    for (genero in libro.generos) {
                            if (genero.id == id) {
                                sortedLibros.add(libro)
                        }
                    }
                }
                sortedLibros.sortByDescending { it.calificacion }
                val adapter = (binding.lstLibros.adapter as LibroAdapter)
                adapter.updateData(sortedLibros)
            }else{
                librosList.sortByDescending { it.calificacion }
                val adapter = (binding.lstLibros.adapter as LibroAdapter)
                adapter.updateData(librosList)
            }
        }

    }

    private fun setupRecyclerView() {
        binding.lstLibros.apply {
            this.adapter = LibroAdapter(ArrayList(), this@MainActivity)
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    override fun onLibrosClick(libro: Libro) {
        Log.d("Libroooooooooooooooo", libro.id.toString())
        val intent = Intent(this, LibroDetailActivity::class.java)
        intent.putExtra("libroId", libro.id)
        startActivity(intent)
    }

    private fun setupEventListeners() {
        binding.btnAnadir.setOnClickListener {
            val intent = Intent(this, LibroSaveActivity::class.java)
            startActivity(intent)
        }
    }
}