package com.example.practico4.models

typealias Libros = ArrayList<Libro>

data class Libro(
    val nombre: String,
    val autor: String,
    val editorial: String,
    val isbn: String,
    val imagen : String,
    val sinopsis : String,
    val calificacion: Int
){
    var id: Int? = null
    val generos: Generos = ArrayList()
}