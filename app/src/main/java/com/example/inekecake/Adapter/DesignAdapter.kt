package com.example.inekecake.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.inekecake.Model.DesignDashModel
import com.example.inekecake.R

class DesignAdapter(val list: ArrayList<DesignDashModel>): RecyclerView.Adapter<DesignAdapter.DesignViewHolder>() {
    inner class DesignViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imageDesign: ImageView = itemView.findViewById(R.id.iv_design_dashboard)
        val stepTextView: TextView = itemView.findViewById(R.id.tv_step_design_dashboard)
        val descTextView: TextView = itemView.findViewById(R.id.tv_desc_design_dashboard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DesignAdapter.DesignViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_design_dashboard, parent, false)
        return DesignViewHolder(view)
    }

    override fun onBindViewHolder(holder: DesignAdapter.DesignViewHolder, position: Int) {
        val data = list[position]
        holder.imageDesign.setImageResource(data.image)
        holder.stepTextView.setText(data.step)
        holder.descTextView.setText(data.desc)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}