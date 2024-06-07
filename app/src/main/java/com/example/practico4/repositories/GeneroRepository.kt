package com.example.practico4.repositories

import android.util.Log
import com.example.practico4.api.APILibroService
import com.example.practico4.models.Genero
import com.example.practico4.models.Generos
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback
object GeneroRepository {

    fun getGeneroList(success: (Generos?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APILibroService =
            retrofit.create(APILibroService::class.java)

        service.getGeneros().enqueue(object : Callback<Generos> {
            override fun onResponse(res: Call<Generos>, response: Response<Generos>) {
                val postList = response.body()
                Log.d("CategoriasSuccess", postList.toString())
                success(postList)
            }

            override fun onFailure(res: Call<Generos>, t: Throwable) {
                Log.d("CategoriasFailure", t.message.toString())
                failure(t)
            }
        })
    }

    fun getGenero(id: Int, success: (Genero?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APILibroService =
            retrofit.create(APILibroService::class.java)

        service.getGeneroById(id).enqueue(object : Callback<Genero?> {
            override fun onResponse(res: Call<Genero?>, response: Response<Genero?>) {
                success(response.body())
            }

            override fun onFailure(res: Call<Genero?>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun insertGenero(
        genero: Genero,
        success: (Genero) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APILibroService =
            retrofit.create(APILibroService::class.java)
        service.insertGenero(genero).enqueue(object : Callback<Genero> {
            override fun onResponse(res: Call<Genero>, response: Response<Genero>) {
                val objGenero = response.body()
                success(objGenero!!)
            }

            override fun onFailure(res: Call<Genero>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun updateGenero(
        genero: Genero,
        id: Int,
        success: (Genero) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APILibroService =
            retrofit.create(APILibroService::class.java)
        service.updateGenero(genero, id).enqueue(object : Callback<Genero> {
            override fun onResponse(res: Call<Genero>, response: Response<Genero>) {
                val objGenero = response.body()
                success(objGenero!!)
            }

            override fun onFailure(res: Call<Genero>, t: Throwable) {
                failure(t)
            }
        })


    }

    fun deleteGenero(
        id: Int,
        success: () -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APILibroService =
            retrofit.create(APILibroService::class.java)
        service.deleteGenero(id).enqueue(object : Callback<Void> {
            override fun onResponse(res: Call<Void>, response: Response<Void>) {
                success()
            }

            override fun onFailure(res: Call<Void>, t: Throwable) {
                failure(t)
            }
        })
    }
}