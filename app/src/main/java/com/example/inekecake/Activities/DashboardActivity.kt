package com.example.inekecake.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inekecake.Adapter.DesignAdapter
import com.example.inekecake.Adapter.KueMarmerAdapter
import com.example.inekecake.Adapter.TartReadyAdapter
import com.example.inekecake.Data.DesignDashData
import com.example.inekecake.Data.KueMarmerDashData
import com.example.inekecake.Data.TartReadyDashData
import com.example.inekecake.Model.DesignDashModel
import com.example.inekecake.Model.KueMarmerModel
import com.example.inekecake.Model.TartReadyDashModel
import com.example.inekecake.R

@Suppress("DEPRECATION")
class DashboardActivity : AppCompatActivity() {

    private lateinit var listDesignDashboard: ArrayList<DesignDashModel>
    private lateinit var listTartReadyDashboard: ArrayList<TartReadyDashModel>
    private lateinit var listKueMarmerDashboard: ArrayList<KueMarmerModel>
    private lateinit var rvDesign: RecyclerView
    private lateinit var rvTartReady: RecyclerView
    private lateinit var rvKueMarmer: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        // set no dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        supportActionBar?.hide()

        listDesignDashboard = DesignDashData().getList
        listTartReadyDashboard = TartReadyDashData().getList
        listKueMarmerDashboard = KueMarmerDashData().getList

        rvDesign = findViewById(R.id.rv_design_cake)
        rvTartReady = findViewById(R.id.rv_tartReady)
        rvKueMarmer = findViewById(R.id.rv_kueMarmer)

        showRecyclerDesign()
        showRecyclerTartReady()
        showRecyclerKueMarmer()


    }

    private fun showRecyclerDesign() {
        rvDesign.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvDesign.adapter = DesignAdapter(listDesignDashboard)
    }

    private fun showRecyclerTartReady() {
        rvTartReady.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvTartReady.adapter = TartReadyAdapter(listTartReadyDashboard)
    }

    private fun showRecyclerKueMarmer() {
        rvKueMarmer.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvKueMarmer.adapter = KueMarmerAdapter(listKueMarmerDashboard)
    }

}