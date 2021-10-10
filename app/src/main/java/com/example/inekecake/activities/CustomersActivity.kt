package com.example.inekecake.activities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.inekecake.api.APIRequestData
import com.example.inekecake.api.RetroServer
import com.example.inekecake.adapter.CustomerAdapter
import com.example.inekecake.model.DataModel
import com.example.inekecake.model.ResponseModel
import com.example.inekecake.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomersActivity : AppCompatActivity(),
    View.OnClickListener,
    CustomerAdapter.OnItemCallback{

    private lateinit var rvCustomers: RecyclerView
    private lateinit var lmCustomers: RecyclerView.LayoutManager
    private lateinit var listCustomers: ArrayList<DataModel>
    private lateinit var srlData: SwipeRefreshLayout
    private lateinit var pbData: ProgressBar
    private lateinit var fabData: FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        // set no dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_customers)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Data Customers"

        // Set Recyclerview
        rvCustomers = findViewById(R.id.rv_customers)
        lmCustomers = LinearLayoutManager(this)
        rvCustomers.layoutManager = lmCustomers

        // Set SRL dan PB
        srlData = findViewById(R.id.srl_data)
        pbData = findViewById(R.id.pb_data)

        // Set FAB
        fabData = findViewById(R.id.fab_data)
        fabData.setOnClickListener(this)

        srlData.setOnRefreshListener {
            retrieveData()
        }

    }

    override fun onResume() {
        super.onResume()
        retrieveData()
    }

    private fun retrieveData() {
        // srl dan pb refreshing
        srlData.isRefreshing = true
        pbData.isVisible = true

        val ardData: APIRequestData = RetroServer.konekRetrofit().create(APIRequestData::class.java)
        val tampilData: Call<ResponseModel> = ardData.ardRetrieveData()

        tampilData.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                val message: String = response.body()?.message ?: "kosong"
                listCustomers = response.body()?.data ?: arrayListOf()

                // set adapter
                rvCustomers.adapter = CustomerAdapter(listCustomers, this@CustomersActivity)

                // set srl dan pb hilang
                srlData.isRefreshing = false
                pbData.isVisible = false

            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                Toast.makeText(this@CustomersActivity, "Gagal Menghubungi Server, ${t.message}", Toast.LENGTH_LONG).show()

                // set srl dan pb hilang
                srlData.isRefreshing = false
                pbData.isVisible = false
            }

        })
    }

    private fun deleteData(id: Int) {
        val ardData: APIRequestData = RetroServer.konekRetrofit().create(APIRequestData::class.java)
        val hapusData = ardData.ardDeleteData(id)

        hapusData.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                val message = response.body()?.message
                retrieveData()
                Toast.makeText(this@CustomersActivity, "$message", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                Toast.makeText(this@CustomersActivity, "${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fab_data -> {
                val intent = Intent(this, CreateDataActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onItemClicked(data: DataModel) {
        val alertDialogBuilder = MaterialAlertDialogBuilder(this)
        alertDialogBuilder.setTitle("Actions")
        alertDialogBuilder.setCancelable(true)

        var checkedItem: Int = 0

        alertDialogBuilder.setSingleChoiceItems(arrayOf("Edit", "Hapus"), 0, object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                checkedItem = which
            }
        })

        alertDialogBuilder.setNegativeButton("Cancel", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
            }
        })

        alertDialogBuilder.setPositiveButton("OK", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when(checkedItem) {
                    0 -> {
                        val intent = Intent(this@CustomersActivity, UpdateDataActivity::class.java)
                        intent.putExtra("dataEdit", data)
                        startActivity(intent)
                    }

                    1 -> {
                        deleteData(data.id)
                    }
                }
            }

        })
        alertDialogBuilder.show()
    }
}