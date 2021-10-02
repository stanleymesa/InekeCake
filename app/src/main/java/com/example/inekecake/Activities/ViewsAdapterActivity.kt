package com.example.inekecake.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inekecake.Adapter.KueMarmerViewsAdapter
import com.example.inekecake.Model.KueMarmerModel
import com.example.inekecake.R
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ViewsAdapterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var rvActivity: RecyclerView
    private lateinit var firestoreRoot: FirebaseFirestore
    private lateinit var ivBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        // set no dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_views_adapter)

        supportActionBar?.hide()

        ivBack = findViewById(R.id.iv_back_kuemarmer_views)
        rvActivity = findViewById(R.id.rv_views_adapter)
        firestoreRoot = Firebase.firestore

        showRecyclerKueMarmer()


        ivBack.setOnClickListener(this)


    }

    private fun showRecyclerKueMarmer() {
        rvActivity.layoutManager = LinearLayoutManager(this)

        firestoreRoot.collection("kue_marmer")
            .addSnapshotListener(this, object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {

                    if (error != null) {
                        return
                    }

                    if (!value!!.isEmpty) {
                        val listKueMarmer = arrayListOf<KueMarmerModel>()
                        for (ds in value) {
                            listKueMarmer.add(ds.toObject(KueMarmerModel::class.java))
                        }
                        rvActivity.adapter = KueMarmerViewsAdapter(listKueMarmer)
                    }

                    else {
                        return
                    }

                }

            })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_back_kuemarmer_views -> {
                startActivity(Intent(this, DashboardActivity::class.java))
                finish()
            }
        }
    }
}