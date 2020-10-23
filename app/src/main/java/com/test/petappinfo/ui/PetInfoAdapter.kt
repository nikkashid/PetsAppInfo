package com.nikhil.petsinfoapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nikhil.petsinfoapp.model.Pet
import kotlinx.android.synthetic.main.cell_pet.view.*

/**
 * Adapter Used to display Pet Info List. the list will display the Image and Title
 */
class PetInfoAdapter(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<PetInfoAdapter.PetInfoViewHolder>() {
    private var petList: List<Pet> = emptyList()

    interface OnItemClickListener {
        fun onItemClick(item: Pet)
    }

    fun setResults(results: List<Pet>) {
        petList = results
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetInfoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(com.nikhil.petsinfoapp.R.layout.cell_pet, parent, false)
        return PetInfoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return petList.size
    }

    override fun onBindViewHolder(holder: PetInfoViewHolder, position: Int) {
        holder.title.text = petList[position].title

        holder.bind(petList[position], listener)

        Glide.with(holder.petImage.context).load(petList[position].image_url)
            .into(holder.petImage)
    }

    class PetInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.title
        val petImage: ImageView = itemView.img_pet

        fun bind(item: Pet, listener: OnItemClickListener) {
            itemView.setOnClickListener { listener.onItemClick(item) }
        }
    }
}