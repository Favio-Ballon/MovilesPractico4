package com.example.practico4.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practico4.models.Libros
import com.example.practico4.repositories.LibroRepository

class MainViewModel : ViewModel() {
    private val _librosList: MutableLiveData<Libros> by lazy {
        MutableLiveData<Libros>(Libros())
    }
    val librosList: LiveData<Libros> get() = _librosList


    fun fetchListaLibros() {
        Log.d("MainViewModel", "fetchListaPersonas")
        LibroRepository.getLibroList(
            success = { libros ->
                libros?.let {
                    _librosList.value = it
                }
            },
            failure = {
                it.printStackTrace()
            })

    }


}