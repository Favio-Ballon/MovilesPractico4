package com.example.practico4.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practico4.models.Generos
import com.example.practico4.repositories.GeneroRepository

class GeneroViewModel: ViewModel() {

    private val _generosList: MutableLiveData<Generos> by lazy {
        MutableLiveData<Generos>(Generos())
    }
    val generosList: LiveData<Generos> get() = _generosList



    fun fetchListaGeneros() {
        GeneroRepository.getGeneroList(
            success = { generos ->
                generos?.let {
                    _generosList.value = it
                }
            },
            failure = {
                it.printStackTrace()
            })
    }

    fun deleteGenero(id: Int) {
        GeneroRepository.deleteGenero(
            id,
            success = {
                fetchListaGeneros()
            },
            failure = {
                it.printStackTrace()
            })
    }


}