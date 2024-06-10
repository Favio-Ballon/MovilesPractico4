package com.example.practico4.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.practico4.models.Generos

import com.example.practico4.databinding.LibroGeneroItemLayoutBinding
import com.example.practico4.models.Genero

class LibroGeneroAdapter(val GeneroList: Generos, val listener: OnLibroGeneroClickListener ) :
    RecyclerView.Adapter<LibroGeneroAdapter.LibroGeneroViewHolder>() {

        var generosId = ArrayList<Int>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibroGeneroViewHolder {
        val binding =
            LibroGeneroItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return LibroGeneroViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return GeneroList.size
    }

    override fun onBindViewHolder(holder: LibroGeneroViewHolder, position: Int) {
        val genero = GeneroList[position]
        holder.bind(genero, generosId, listener)
    }

    fun updateData(newGeneroList: List<Genero>, generosId: ArrayList<Int>) {
        GeneroList.clear()
        this.generosId = generosId
        GeneroList.addAll(newGeneroList)
        notifyDataSetChanged()
    }

    class LibroGeneroViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(genero: Genero, generosId: ArrayList<Int>, listener: OnLibroGeneroClickListener) {
            val binding = LibroGeneroItemLayoutBinding.bind(itemView)
            binding.apply {
                btntoggleGenero.id = genero.id!!
                btntoggleGenero.text =genero.nombre
                btntoggleGenero.textOn = genero.nombre
                btntoggleGenero.textOff = genero.nombre

                btntoggleGenero.setOnCheckedChangeListener { buttonView, isChecked ->
                        listener.onLibroGeneroClick(genero)
                        }
                if (generosId.contains(genero.id)) {
                    btntoggleGenero.isChecked = true
                }

            }

        }
    }

    interface OnLibroGeneroClickListener {
        fun onLibroGeneroClick(genero: Genero)

    }


}
