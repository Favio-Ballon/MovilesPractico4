package com.example.practico4.repositories

import android.util.Log
import com.example.practico4.api.APILibroService
import com.example.practico4.models.Libro
import com.example.practico4.models.Libros
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback


object LibroRepository {
    fun getLibroList(success: (Libros?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APILibroService =
            retrofit.create(APILibroService::class.java)

        service.getLibros().enqueue(object : Callback<Libros> {
            override fun onResponse(res: Call<Libros>, response: Response<Libros>) {
                val postList = response.body()
                Log.d("CategoriasSuccess", postList.toString())
                success(postList)
            }

            override fun onFailure(res: Call<Libros>, t: Throwable) {
                Log.d("CategoriasFailure", t.message.toString())
                failure(t)
            }
        })
        }

    fun getLibro(id: Int, success: (Libro?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APILibroService =
            retrofit.create(APILibroService::class.java)

        service.getLibroById(id).enqueue(object : Callback<Libro?> {
            override fun onResponse(res: Call<Libro?>, response: Response<Libro?>) {
                success(response.body())
            }

            override fun onFailure(res: Call<Libro?>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun insertLibro(
        libro: Libro,
        success: (Libro) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APILibroService =
            retrofit.create(APILibroService::class.java)
        service.insertLibro(libro).enqueue(object : Callback<Libro> {
            override fun onResponse(res: Call<Libro>, response: Response<Libro>) {
                val objLibro = response.body()
                success(objLibro!!)
            }

            override fun onFailure(res: Call<Libro>, t: Throwable) {
                failure(t)
            }
        })
    }



}

