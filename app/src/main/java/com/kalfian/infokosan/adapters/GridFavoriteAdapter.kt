package com.kalfian.infokosan.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kalfian.infokosan.R
import com.kalfian.infokosan.databinding.GridItemKosBinding
import com.kalfian.infokosan.models.favorite.Favorite
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class GridFavoriteAdapter(onClick: AdapterFavoriteOnClickListener): RecyclerView.Adapter<GridFavoriteAdapter.ViewHolder>() {

    private var list = ArrayList<Favorite>()
    private var onClickAdapter = onClick

    inner class ViewHolder(itemView: View, onClickListener: AdapterFavoriteOnClickListener): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val b = GridItemKosBinding.bind(itemView)
        private var clickListener: AdapterFavoriteOnClickListener = onClickListener

        fun bind(fav: Favorite) {

            b.favoriteKos.isSelected = true
            b.addressKos.text = fav.alamat
            b.titleKos.text = fav.title

            val localeID =  Locale("in", "ID")
            val numberFormat = NumberFormat.getCurrencyInstance(localeID)
            val formattedNumber: String = numberFormat.format(fav.harga).toString()
            b.priceKos.text = formattedNumber

            var image = fav.image

            Picasso.get()
                .load(image)
                .placeholder(R.drawable.logo)
                .into(b.imageKos)

            itemView.setOnClickListener(this)

        }

        override fun onClick(v: View?) {
            clickListener.onFavoriteClickListener(list[adapterPosition])
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

    fun addList(items: ArrayList<Favorite>) {
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }

    interface AdapterFavoriteOnClickListener {
        fun onFavoriteClickListener(data: Favorite)
    }

}