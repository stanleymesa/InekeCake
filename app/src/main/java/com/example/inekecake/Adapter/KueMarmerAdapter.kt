package com.example.inekecake.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.inekecake.Model.KueMarmerModel
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
        holder.kueMarmerImage.setImageResource(data.image)
        holder.name.setText(data.name)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}