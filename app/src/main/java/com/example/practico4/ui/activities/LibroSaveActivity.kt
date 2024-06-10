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
import com.bumptech.glide.Glide
import com.example.practico4.R
import com.example.practico4.databinding.ActivityLibroSaveBinding
import com.example.practico4.ui.viewmodels.LibroSaveViewModel

class LibroSaveActivity : AppCompatActivity() {
    private var id: Int = -1
    lateinit var binding: ActivityLibroSaveBinding
    private val model: LibroSaveViewModel by viewModels()
    private var generosId = ArrayList<Int>()
    private val REQUEST_CODE = 1

    private var generosNew : ArrayList<Int>? = null
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_generos) {
            val intent = Intent(this, LibroSaveGeneroActivity::class.java)

            if ((generosNew?.size ?: 0) > 0) {
                intent.putExtra("libroId", generosNew)
            }else {
                intent.putExtra("libroId", generosId)
            }

            startActivityForResult(intent, REQUEST_CODE)
            return true
        }

        return super.onOptionsItemSelected(item)

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
                binding.txtCalificacion.editText?.text.toString().toInt(),
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

            for (genero in it.generos) {
                generosId.add(genero.id!!)
            }


        }
    }

    private fun setupViewModelObservers() {
        model.closeActivity.observe(this) {
            if (it) {
                finish()
            }
        }

        model.editarGeneros.observe(this) {
            if (it) {
                if (id != -1) {
                    Log.d("LibroSaveActivity", "Entro al editar generos")
                    model.editarGeneros(generosId, generosNew, id)
                }else{
                    Log.d("LibroSaveActivity", "Entro al lastId")
                    model.getLastId()
                }
            }
        }

        model.librosList.observe(this) {
            if (it != null) {

                id = it[it.size - 1].id!!
                Log.d("LibroSaveActivity", id.toString())

                model.editarGeneros(generosId, generosNew,id)
            }
        }

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            generosNew = data?.getIntegerArrayListExtra("libroId")
            Log.d("generos", generosNew.toString())
            }
    }

}