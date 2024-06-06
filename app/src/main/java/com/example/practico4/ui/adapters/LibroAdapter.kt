package com.example.practico4.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practico4.databinding.LibroItemLayoutBinding
import com.example.practico4.models.Libro
import com.example.practico4.models.Libros

class LibroAdapter(val libroList: Libros, val listener: OnLibrosClickListener) :
    RecyclerView.Adapter<LibroAdapter.LibroViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibroViewHolder {
        val binding =
            LibroItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return LibroViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: LibroAdapter.LibroViewHolder, position: Int) {
        val libro = libroList[position]
        holder.bind(libro, listener)
    }

    override fun getItemCount(): Int {
        return libroList.size
    }


    fun updateData(libroList: Libros) {
        this.libroList.clear()
        this.libroList.addAll(libroList)
        notifyDataSetChanged()
    }


    class LibroViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(libro: Libro, listener : OnLibrosClickListener) {
            val binding = LibroItemLayoutBinding.bind(itemView)
            binding.apply {

                lblLibroName.text = libro.nombre
                lblLibroAutor.text = libro.autor
                lblLibroRating.text = libro.calificacion.toString()

                // Carga la imagen desde la url con GLide
                if (libro.imagen != null) {
                    Log.d("imagen", libro.imagen)
                    Glide.with(itemView)
                        .load(libro.imagen)
                        .into(imagenLibro)
                }

                //set size of image



                root.setOnClickListener { listener.onLibrosClick(libro) }
            }

        }
    }


    interface OnLibrosClickListener {
        fun onLibrosClick(libro: Libro)
    }

}
