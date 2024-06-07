package com.example.practico4.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practico4.models.Libro
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
                    _closeActivity.value = true
                },
                failure = {
                    it.printStackTrace()
                })
        }else {

            LibroRepository.insertLibro(
                libro,
                success = {
                    _closeActivity.value = true
                },
                failure = {
                    it.printStackTrace()
                })
        }

    }



}