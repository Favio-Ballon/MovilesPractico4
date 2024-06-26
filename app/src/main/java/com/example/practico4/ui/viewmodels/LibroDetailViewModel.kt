package com.example.practico4.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practico4.models.Libro
import com.example.practico4.repositories.LibroRepository

class LibroDetailViewModel : ViewModel() {


    private val _libro: MutableLiveData<Libro?> by lazy {
        MutableLiveData<Libro?>(null)
    }
    val libro: LiveData<Libro?> get() = _libro

    private val _closeActivity: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    val closeActivity: LiveData<Boolean> get() = _closeActivity

    fun loadCategory(id: Int) {
        LibroRepository.getLibro(id,
            success = {
                _libro.value = it
            },
            failure = {
                it.printStackTrace()
            })
    }

    fun deleteLibro(id: Int) {
        LibroRepository.deleteLibro(
            id,
            success = {
                _closeActivity.value = true
            },
            failure = {
                it.printStackTrace()
            })
    }

}