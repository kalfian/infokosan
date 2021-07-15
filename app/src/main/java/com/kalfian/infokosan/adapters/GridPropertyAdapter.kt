package com.kalfian.infokosan.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.kalfian.infokosan.R
import com.kalfian.infokosan.databinding.GridItemKosBinding
import com.kalfian.infokosan.models.properties.Property
import com.kalfian.infokosan.utils.Midtrans
import com.kalfian.infokosan.utils.listen
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class GridPropertyAdapter(onClick: AdapterPropertyOnClickListener): RecyclerView.Adapter<GridPropertyAdapter.ViewHolder>() {

    private var list = ArrayList<Property>()
    private var onClickAdapter = onClick

    inner class ViewHolder(itemView: View, onClickListener: AdapterPropertyOnClickListener): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val b = GridItemKosBinding.bind(itemView)
        private var clickListener: AdapterPropertyOnClickListener = onClickListener

        fun bind(property: Property) {

            b.favoriteKos.isSelected = true
            b.addressKos.text = property.location.address
            b.titleKos.text = property.title

            val localeID =  Locale("in", "ID")
            val numberFormat = NumberFormat.getCurrencyInstance(localeID)
            val formattedNumber: String = numberFormat.format(property.basicPrice).toString()
            b.priceKos.text = formattedNumber

            var image = if(property.propertyImages.isNotEmpty()) property.propertyImages[0].image else "-"

            Picasso.get()
                .load(image)
                .placeholder(R.drawable.logo)
                .into(b.imageKos)

            itemView.setOnClickListener(this)

        }

        override fun onClick(v: View?) {
            clickListener.onPropertyClickListener(list[adapterPosition])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.grid_item_kos, parent, false)
        return ViewHolder(v, onClickAdapter)
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

    interface AdapterPropertyOnClickListener {
        fun onPropertyClickListener(data: Property)
    }

}