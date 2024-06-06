package com.example.practico4.models

typealias Libros = ArrayList<Libro>

data class Libro(
    val id: Int,
    val nombre: String,
    val autor: String,
    val ISBN: String,
    val imagen : String,
    val sinopsis : String,
    val calificacion: Int
)