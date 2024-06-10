package com.example.practico4.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practico4.models.Genero
import com.example.practico4.repositories.GeneroRepository
import com.example.practico4.repositories.LibroRepository

class GeneroSaveViewModel : ViewModel() {

    private val _closeActivity: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    val closeActivity: LiveData<Boolean> get() = _closeActivity

    private val _genero: MutableLiveData<Genero?> by lazy {
        MutableLiveData<Genero?>(null)
    }

    val genero: LiveData<Genero?> get() = _genero

    fun loadGenero(id: Int) {
        GeneroRepository.getGenero(id,
            success = {
                _genero.value = it
            },
            failure = {
                it.printStackTrace()
            })
    }

    fun saveGenero(
        id: Int,
        nombre: String
    ) {

        val genero = Genero(
            nombre = nombre
        )

        if (id != -1) {
            genero.id = 0
            GeneroRepository.updateGenero(
                genero,
                id,
                success = {
                    _closeActivity.value = true
                },
                failure = {
                    it.printStackTrace()
                })
        } else {
            GeneroRepository.insertGenero(
                genero,
                success = {
                    _closeActivity.value = true
                },
                failure = {
                    it.printStackTrace()
                })
        }
    }
}