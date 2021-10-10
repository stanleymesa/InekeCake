package com.example.inekecake.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.inekecake.model.TartReadyDashModel
import com.example.inekecake.R

class TartReadyAdapter(val list: ArrayList<TartReadyDashModel>): RecyclerView.Adapter<TartReadyAdapter.TartReadyViewHolder>() {
    inner class TartReadyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tartReadyImage: ImageView = itemView.findViewById(R.id.iv_tartready_dashboard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TartReadyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_tartready_dashboard, parent, false)
        return TartReadyViewHolder(view)
    }

    override fun onBindViewHolder(holder: TartReadyViewHolder, position: Int) {
        val data = list[position]
        holder.tartReadyImage.setImageResource(data.image)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}