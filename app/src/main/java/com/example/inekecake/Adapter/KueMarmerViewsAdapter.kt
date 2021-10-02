package com.example.inekecake.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.inekecake.R
import com.example.inekecake.Model.KueMarmerModel

class KueMarmerViewsAdapter(val list: ArrayList<KueMarmerModel>) : RecyclerView.Adapter<KueMarmerViewsAdapter.KueMarmerViewsViewHolder>() {

    inner class KueMarmerViewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val kueMarmerImage: ImageView = itemView.findViewById(R.id.iv_kuemarmer_views)
        val nama: TextView = itemView.findViewById(R.id.tv_kuemarmer_views)
        val harga: TextView = itemView.findViewById(R.id.tv_harga_kuemarmer_views)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KueMarmerViewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_kuemarmer_views, parent, false)
        return KueMarmerViewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: KueMarmerViewsViewHolder, position: Int) {
        val data = list[position]
        Glide.with(holder.itemView.context)
            .load(data.url)
            .into(holder.kueMarmerImage)
        holder.nama.text = data.nama
        holder.harga.text = data.harga
    }

    override fun getItemCount(): Int {
        return list.size
    }
}