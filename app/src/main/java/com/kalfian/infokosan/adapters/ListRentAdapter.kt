package com.kalfian.infokosan.adapters

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kalfian.infokosan.R
import com.kalfian.infokosan.databinding.ListItemRentBinding
import com.kalfian.infokosan.models.properties.Property
import com.kalfian.infokosan.models.rent.DataRent
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class ListRentAdapter(onClick: AdapterRentOnClickListener): RecyclerView.Adapter<ListRentAdapter.ViewHolder>() {

    private var list = ArrayList<DataRent>()
    private var onClickAdapter = onClick

    inner class ViewHolder(itemView: View, onClickListener: AdapterRentOnClickListener): RecyclerView.ViewHolder(itemView) {
        private val b = ListItemRentBinding.bind(itemView)
        private var clickListener: AdapterRentOnClickListener = onClickListener

        fun bind(data: DataRent) {

            // Status Lunas
            if(data.activeStatus != 2) {
                b.btnBayar.visibility = View.VISIBLE
            } else {
                b.btnBayar.visibility = View.GONE
            }

            val price = data.payment.adminCost + data.payment.amount
            val localeID =  Locale("in", "ID")
            val numberFormat = NumberFormat.getCurrencyInstance(localeID)
            val formattedNumber: String = numberFormat.format(price).toString()
            b.totalTagihan.text = formattedNumber

            var image = if(data.property.gallery.isNotEmpty()) "https://infokosan.kryptonraven.com/storage/"+data.property.gallery[0].file else "-"


            Picasso.get()
                .load(image)
                .placeholder(R.drawable.logo)
                .into(b.imageKos)

            b.btnBayar.setOnClickListener {
                clickListener.onBayarClickListener(list[adapterPosition])
            }



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

    fun addList(items: ArrayList<DataRent>) {
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }

    interface AdapterRentOnClickListener {
        fun onBayarClickListener(data: DataRent)
    }

}