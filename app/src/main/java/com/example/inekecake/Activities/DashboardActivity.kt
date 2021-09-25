package com.example.inekecake.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
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
import com.example.inekecake.Session.SessionManager
import com.google.android.material.navigation.NavigationView

@Suppress("DEPRECATION")
class DashboardActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var listDesignDashboard: ArrayList<DesignDashModel>
    private lateinit var listTartReadyDashboard: ArrayList<TartReadyDashModel>
    private lateinit var listKueMarmerDashboard: ArrayList<KueMarmerModel>
    private lateinit var rvDesign: RecyclerView
    private lateinit var rvTartReady: RecyclerView
    private lateinit var rvKueMarmer: RecyclerView
    private lateinit var btnLogout: Button
    private lateinit var rememberMeSession: SessionManager
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var ivMenu: ImageView

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
        btnLogout = findViewById(R.id.btn_logout)
        ivMenu = findViewById(R.id.iv_menuBar)

        // DRAWER NAV
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_drawer)
        setNavigationDrawer()
        // END DRAWER NAV

        showRecyclerDesign()
        showRecyclerTartReady()
        showRecyclerKueMarmer()

        btnLogout.setOnClickListener(this)
        ivMenu.setOnClickListener(this)

        // SET SESSION
        rememberMeSession = SessionManager(this, SessionManager.REMEMBERME_SESSION)

    }

    private fun setNavigationDrawer() {
        navigationView.bringToFront()
        navigationView.setCheckedItem(R.id.menu_drawer_home)



        navigationView.setNavigationItemSelectedListener(object : NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                navigationView.setCheckedItem(item.itemId)
                return true
            }

        })
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

    override fun onClick(v: View?) {
        when(v?.id)  {
            R.id.btn_logout -> {
                rememberMeSession.clearRememberMe()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.iv_menuBar -> {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
    }

}