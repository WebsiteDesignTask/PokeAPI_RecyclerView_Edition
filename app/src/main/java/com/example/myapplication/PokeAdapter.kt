package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PokeAdapter(val pokeList: MutableList<Pokemon>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.poke_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pokeList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val pokemon = pokeList[position]
        val viewHolder = holder as ViewHolder
        viewHolder.pokeName.text = pokemon.name
        viewHolder.pokeType.text = pokemon.type
        viewHolder.pokeLoc.text = pokemon.height
        Glide.with(viewHolder.itemView.context)
            .load(pokemon.imageUrl)
            .into(viewHolder.pokeImage)
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pokeImage: ImageView = itemView.findViewById(R.id.poke_image)
        val pokeName: TextView = itemView.findViewById(R.id.poke_name)
        val pokeType: TextView = itemView.findViewById(R.id.poke_type)
        val pokeLoc: TextView = itemView.findViewById(R.id.poke_height)
    }

}