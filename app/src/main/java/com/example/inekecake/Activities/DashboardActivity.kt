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
import android.widget.LinearLayout
import android.widget.Toast
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
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

@Suppress("DEPRECATION")
class DashboardActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var listDesignDashboard: ArrayList<DesignDashModel>
    private lateinit var listTartReadyDashboard: ArrayList<TartReadyDashModel>
    private lateinit var rvDesign: RecyclerView
    private lateinit var rvTartReady: RecyclerView
    private lateinit var rvKueMarmer: RecyclerView
    private lateinit var btnLogout: Button
    private lateinit var viewAllTartReady: LinearLayout
    private lateinit var viewAllKueMarmer: LinearLayout
    private lateinit var rememberMeSession: SessionManager
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var ivMenu: ImageView
    private lateinit var firestoreRoot: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        // set no dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        supportActionBar?.hide()

        // SET FIREBASE
        firestoreRoot = Firebase.firestore

        listDesignDashboard = DesignDashData().getList
        listTartReadyDashboard = TartReadyDashData().getList

        rvDesign = findViewById(R.id.rv_design_cake)
        rvTartReady = findViewById(R.id.rv_tartReady)
        rvKueMarmer = findViewById(R.id.rv_kueMarmer)
        btnLogout = findViewById(R.id.btn_logout)
        ivMenu = findViewById(R.id.iv_menuBar)
        viewAllTartReady = findViewById(R.id.layout_viewAllTartReady)
        viewAllKueMarmer = findViewById(R.id.layout_viewAllKueMarmer)

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
        viewAllKueMarmer.setOnClickListener(this)


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

        val query = firestoreRoot.collection("kuemarmer_dash")
        query.addSnapshotListener(this, object : EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null) {
                    return
                }

                if (!value!!.isEmpty) {
                    val listKueMarmer = arrayListOf<KueMarmerModel>()
                    for (ds in value) {
                        listKueMarmer.add(ds.toObject(KueMarmerModel::class.java))
                    }
                    rvKueMarmer.adapter = KueMarmerAdapter(listKueMarmer)
                }

                else {
                    return
                }
            }

        })
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
            R.id.layout_viewAllKueMarmer -> {
                val intent = Intent(this, ViewsAdapterActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

}