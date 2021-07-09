package com.kalfian.infokosan.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kalfian.infokosan.R
import com.kalfian.infokosan.databinding.GridItemKosBinding
import com.kalfian.infokosan.models.properties.Property
import com.squareup.picasso.Picasso


class GridPropertyAdapter: RecyclerView.Adapter<GridPropertyAdapter.ViewHolder>() {

    private var list = ArrayList<Property>()

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val b = GridItemKosBinding.bind(itemView)

        fun bind(property: Property) {

            b.favoriteKos.isSelected = true
            b.addressKos.text = property.location.address
            b.titleKos.text = property.title

            Picasso.get()
                .load(property.propertyImages[0].image)
                .placeholder(R.drawable.logo)
                .into(b.imageKos);
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.grid_item_kos, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addList(items: ArrayList<Property>) {
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }
}