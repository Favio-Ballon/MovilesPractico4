package com.example.practico4.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.practico4.R
import com.example.practico4.databinding.ActivityLibroSaveGeneroBinding
import com.example.practico4.models.Genero
import com.example.practico4.ui.adapters.LibroGeneroAdapter
import com.example.practico4.ui.viewmodels.LibroSaveGeneroViewModel

class LibroSaveGeneroActivity : AppCompatActivity(), LibroGeneroAdapter.OnLibroGeneroClickListener{
    lateinit var binding: ActivityLibroSaveGeneroBinding
    val model : LibroSaveGeneroViewModel by viewModels()
    var generosId = ArrayList<Int>()
    var generosIdIntent = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLibroSaveGeneroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecyclerView()
        setupViewModelListeners()
        setupEventListeners()
        generosId = intent.getIntegerArrayListExtra("libroId") as ArrayList<Int>

        model.fetchListaGeneros()
    }

    private fun setupEventListeners() {
        binding.btnGuardarLibroGenero.setOnClickListener {
            Log.d("LibroSaveGeneroActivity", "generosId: $generosIdIntent")
            val intent = intent
            intent.putIntegerArrayListExtra("libroId", generosIdIntent)
            setResult(RESULT_OK, intent)
            finish()
        }

    }


    private fun setupViewModelListeners() {

        model.generosList.observe(this) {
            val adapter = (binding.lstBtnGeneros.adapter as LibroGeneroAdapter)
            adapter.updateData(it, generosId)
        }
    }

    private fun setupRecyclerView() {
        binding.lstBtnGeneros .apply {
            this.adapter = LibroGeneroAdapter(ArrayList(), this@LibroSaveGeneroActivity)
            layoutManager = GridLayoutManager(this@LibroSaveGeneroActivity,2)
        }
    }

    override fun onLibroGeneroClick(genero: Genero) {
        Log.d("LibroSaveGeneroActivity", "genero: ${genero.id}")
        if (generosIdIntent.contains(genero.id)) {
            generosIdIntent.remove(genero.id)
        } else {
            generosIdIntent.add(genero.id!!)
        }
    }
}