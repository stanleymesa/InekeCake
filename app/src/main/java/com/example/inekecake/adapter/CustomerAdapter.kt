package com.example.inekecake.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.inekecake.model.DataModel
import com.example.inekecake.R

class CustomerAdapter(val list: ArrayList<DataModel>, val onItemCallback: OnItemCallback) : RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder>() {

    inner class CustomerViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
        var idCustomer: TextView = itemView.findViewById(R.id.tv_id)
        var namaCustomer: TextView = itemView.findViewById(R.id.tv_nama_customer)
        var noHpCustomer: TextView = itemView.findViewById(R.id.tv_noHp_customer)
        var alamatCustomer: TextView = itemView.findViewById(R.id.tv_alamat_customer)
        var cakeCustomer: TextView = itemView.findViewById(R.id.tv_cake_customer)
        var hargaCake: TextView = itemView.findViewById(R.id.tv_harga_cake)
        var tglPesan: TextView = itemView.findViewById(R.id.tv_tglPesan)
        var tglKirim: TextView = itemView.findViewById(R.id.tv_tglKirim)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerAdapter.CustomerViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_customer, parent, false)
        return CustomerViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomerAdapter.CustomerViewHolder, position: Int) {
        val customer: DataModel = list[position]
        holder.idCustomer.text = customer.id.toString()
        holder.namaCustomer.text = customer.nama
        holder.noHpCustomer.text = customer.noHp
        holder.alamatCustomer.text = customer.alamat
        holder.cakeCustomer.text = customer.cake
        holder.hargaCake.text = customer.harga
        holder.tglPesan.text = customer.tglPesan
        holder.tglKirim.text = customer.tglKirim

        holder.itemView.setOnClickListener(){
            onItemCallback.onItemClicked(customer)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemCallback{
        fun onItemClicked(data: DataModel)
    }
}