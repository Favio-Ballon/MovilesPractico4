package com.example.practico4.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practico4.models.Genero
import com.example.practico4.models.Libro
import com.example.practico4.models.LibroGenero
import com.example.practico4.models.Libros
import com.example.practico4.repositories.GeneroRepository
import com.example.practico4.repositories.LibroRepository

class LibroSaveViewModel  : ViewModel() {

    private val _closeActivity: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val closeActivity: LiveData<Boolean> get() = _closeActivity

    private val _libro: MutableLiveData<Libro?> by lazy {
        MutableLiveData<Libro?>(null)
    }
    val libro: LiveData<Libro?> get() = _libro

    private val _editarGeneros: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val editarGeneros: LiveData<Boolean> get() = _editarGeneros

    private val _librosList: MutableLiveData<Libros> by lazy {
        MutableLiveData<Libros>(null)
    }
    val librosList: LiveData<Libros> get() = _librosList


    fun loadCategory(id: Int) {
        LibroRepository.getLibro(id,
            success = {
                _libro.value = it
            },
            failure = {
                it.printStackTrace()
            })
    }



    fun saveLibro(
        id: Int,
        nombre: String,
        autor: String,
        editorial: String,
        ISBN: String,
        imagen : String,
        sinopsis : String,
        calificacion: Int

    ) {

        val libro = Libro(
            nombre = nombre,
            autor = autor,
            editorial = editorial,
            isbn = ISBN,
            imagen = imagen,
            sinopsis = sinopsis,
            calificacion = calificacion
        )

        if (id != -1) {
            Log.d("LibroSaveViewModel", "saveCategory: $id")

            libro.id = 0
            LibroRepository.updateLibro(
                libro,
                id,
                success = {
                    _editarGeneros.value = true
                },
                failure = {
                    it.printStackTrace()
                })
        }else {

            LibroRepository.insertLibro(
                libro,
                success = {
                    _editarGeneros.value = true
                },
                failure = {
                    it.printStackTrace()
                })
        }

    }

    fun editarGeneros(
        generosId : ArrayList<Int>,
        generosNew : ArrayList<Int>? = null,
        id : Int
    )
    {
        if (generosNew != null){
            for (generoId in generosId) {
                if (!generosNew.contains(generoId)) {
                    eliminarGeneroLibro(generoId, id)
                }
            }

            for (generoId in generosNew) {
                if (!generosId.contains(generoId)) {
                    agregarGeneroLibro(generoId, id)
                }
            }
        }
        _closeActivity.value = true
    }

    fun agregarGeneroLibro(
        generoId: Int,
        libroId: Int
    ){
        val generoLibro = LibroGenero(
            genero_id = generoId,
            libro_id = libroId
        )

        GeneroRepository.insertLibroGenero(
            generoLibro,
            success = {
                Log.d("LibroSaveViewModel", "Genero insertado")
            },
            failure = {
                it.printStackTrace()
            }
        )
    }

    fun eliminarGeneroLibro(
        generoId: Int,
        libroId: Int
    ){
        val generoLibro = LibroGenero(
            genero_id = generoId,
            libro_id = libroId
        )

        GeneroRepository.deleteLibroGenero(
            generoLibro,
            success = {
                Log.d("LibroSaveViewModel", "Genero eliminado")
            },
            failure = {
                it.printStackTrace()
            }
        )
    }

    fun getLastId(){
        LibroRepository.getLibroList(
            success = {
                _librosList.value = it
            },
            failure = {
                it.printStackTrace()
            }
        )
    }


}