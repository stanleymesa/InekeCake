package com.example.inekecake.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inekecake.API.APIRequestData
import com.example.inekecake.API.RetroServer
import com.example.inekecake.Adapter.CustomerAdapter
import com.example.inekecake.Model.DataModel
import com.example.inekecake.Model.ResponseModel
import com.example.inekecake.R
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.security.ProviderInstaller
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class CustomersActivity : AppCompatActivity() {

    private lateinit var rvCustomers: RecyclerView
    private lateinit var lmCustomers: RecyclerView.LayoutManager
    private lateinit var listCustomers: ArrayList<DataModel>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        try {
//            ProviderInstaller.installIfNeeded(this)
//        } catch (e: GooglePlayServicesRepairableException) {
//            GoogleApiAvailability.getInstance()
//                .showErrorNotification(this, e.connectionStatusCode)
//        } catch (e: GooglePlayServicesNotAvailableException){}

        setContentView(R.layout.activity_customers)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        // Set Recyclerview
        rvCustomers = findViewById(R.id.rv_customers)
        lmCustomers = LinearLayoutManager(this)
        rvCustomers.layoutManager = lmCustomers
        // End Set Recyclerview

        retrieveData()
    }

    private fun retrieveData() {
        val ardData: APIRequestData = RetroServer.konekRetrofit().create(APIRequestData::class.java)
        val tampilData: Call<ResponseModel> = ardData.ardRetrieveData()

        tampilData.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                val message: String = response.body()?.message ?: "kosong"
                listCustomers = response.body()?.data ?: arrayListOf()
                Toast.makeText(this@CustomersActivity, "Berhasil Menghubungi Server, $message", Toast.LENGTH_SHORT).show()

                rvCustomers.adapter = CustomerAdapter(listCustomers)

            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                Toast.makeText(this@CustomersActivity, "Gagal Menghubungi Server, ${t.message}", Toast.LENGTH_LONG).show()
            }

        })
    }
}