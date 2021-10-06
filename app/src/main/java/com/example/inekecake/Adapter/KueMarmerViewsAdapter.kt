package com.example.inekecake.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.inekecake.Model.CartModel
import com.example.inekecake.R
import com.example.inekecake.Model.KueMarmerModel
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class KueMarmerViewsAdapter(options: FirestoreRecyclerOptions<KueMarmerModel>, val listener: OnKueMarmerClicked, val cartList: ArrayList<CartModel>)
    : FirestoreRecyclerAdapter<KueMarmerModel, KueMarmerViewsAdapter.KueMarmerViewsViewHolder>(options) {


    inner class KueMarmerViewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val kueMarmerImage: ImageView = itemView.findViewById(R.id.iv_kuemarmer_views)
        val nama: TextView = itemView.findViewById(R.id.tv_kuemarmer_views)
        val harga: TextView = itemView.findViewById(R.id.tv_harga_kuemarmer_views)
        val isInCartLayout: RelativeLayout = itemView.findViewById(R.id.layout_isInCart_kuemarmer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KueMarmerViewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_kuemarmer_views, parent, false)
        return KueMarmerViewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: KueMarmerViewsViewHolder, position: Int, model: KueMarmerModel) {
        Glide.with(holder.itemView.context)
            .load(model.url)
            .into(holder.kueMarmerImage)
        holder.nama.text = model.nama
        holder.harga.text = model.harga
        holder.itemView.setOnClickListener { listener.onKueMarmerClicked(model) }

        // CEK CART
        val cekIdCakeFromCart = arrayListOf<String>()
        for (ds in cartList) {
            cekIdCakeFromCart.add(ds.id)
        }

        if (cekIdCakeFromCart.contains(model.id)) {
            holder.isInCartLayout.isVisible = true
        } else {
            holder.isInCartLayout.visibility = View.GONE
        }
    }


    interface OnKueMarmerClicked {
        fun onKueMarmerClicked(data: KueMarmerModel)
    }

}