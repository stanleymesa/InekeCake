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
import com.example.inekecake.Activities.ViewsAdapterActivity
import com.example.inekecake.Model.CartModel
import com.example.inekecake.R
import com.example.inekecake.Model.KueMarmerModel
import com.example.inekecake.Session.SessionManager

class KueMarmerViewsAdapter(val list: ArrayList<KueMarmerModel>, val listener: OnKueMarmerClicked ) : RecyclerView.Adapter<KueMarmerViewsAdapter.KueMarmerViewsViewHolder>() {

    private lateinit var cartSession: SessionManager

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

    override fun onBindViewHolder(holder: KueMarmerViewsViewHolder, position: Int) {
        val data = list[position]
        Glide.with(holder.itemView.context)
            .load(data.url)
            .into(holder.kueMarmerImage)
        holder.nama.text = data.nama
        holder.harga.text = data.harga
        holder.itemView.setOnClickListener { listener.onKueMarmerClicked(data) }

        // AMBIL CART DARI SESSION
        cartSession = SessionManager(holder.itemView.context, SessionManager.CART_SESSION)
        val cartList = cartSession.getCartSession()
        val cekIdCakeFromCart = arrayListOf<String>()
        for (ds in cartList) {
            cekIdCakeFromCart.add(ds.id)
        }

        holder.isInCartLayout.isVisible = cekIdCakeFromCart.contains(data.id)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnKueMarmerClicked {
        fun onKueMarmerClicked(data: KueMarmerModel)
    }
}