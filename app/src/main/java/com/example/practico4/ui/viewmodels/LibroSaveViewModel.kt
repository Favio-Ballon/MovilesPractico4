package com.example.practico4.ui.viewmodels

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

    fun saveLibro(
        nombre: String,
        autor: String,
        editorial: String,
        ISBN: String,
        imagen : String,
        sinopsis : String,
        calificacion: Int
    ) {
        val Libro = Libro(
            nombre = nombre,
            autor = autor,
            editorial = editorial,
            ISBN = ISBN,
            imagen = imagen,
            sinopsis = sinopsis,
            calificacion = calificacion
        )
        LibroRepository.insertLibro(
            Libro,
            success = {
                _closeActivity.value = true
            },
            failure = {
                it.printStackTrace()
            })

    }


}