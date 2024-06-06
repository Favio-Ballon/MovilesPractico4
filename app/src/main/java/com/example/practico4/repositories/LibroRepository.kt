package com.example.practico4.repositories

import android.util.Log
import com.example.practico4.api.APILibroService
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



}

