package com.example.inekecake.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.inekecake.model.KueMarmerModel
import com.example.inekecake.R

class KueMarmerAdapter(val list: ArrayList<KueMarmerModel>): RecyclerView.Adapter<KueMarmerAdapter.KueMarmerViewHolder>() {

    inner class KueMarmerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val kueMarmerImage: ImageView = itemView.findViewById(R.id.iv_kuemarmer_dashboard)
        val name: TextView = itemView.findViewById(R.id.tv_kuemarmer_dashboard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KueMarmerViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_kuemarmer_dashboard, parent, false)
        return KueMarmerViewHolder(view)
    }

    override fun onBindViewHolder(holder: KueMarmerViewHolder, position: Int) {
        val data = list[position]
        Glide.with(holder.itemView.context)
            .load(data.url)
            .into(holder.kueMarmerImage)
        holder.name.text = data.nama
    }

    override fun getItemCount(): Int {
        return list.size
    }
}