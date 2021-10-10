package com.example.inekecake.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inekecake.adapter.KueMarmerViewsAdapter
import com.example.inekecake.model.CartModel
import com.example.inekecake.model.KueMarmerModel
import com.example.inekecake.R
import com.example.inekecake.session.SessionManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ViewsAdapterActivity : AppCompatActivity(), View.OnClickListener,
    KueMarmerViewsAdapter.OnKueMarmerClicked {

    private lateinit var rvActivity: RecyclerView
    private lateinit var firestoreRoot: FirebaseFirestore
    private lateinit var ivBack: ImageView
    private lateinit var loginSession: SessionManager
    private lateinit var cartSession: SessionManager
    private lateinit var userIdSession: String
    private lateinit var getAdapterListener: GetAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        // set no dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_views_adapter)

        supportActionBar?.hide()

        ivBack = findViewById(R.id.iv_back_kuemarmer_views)
        rvActivity = findViewById(R.id.rv_views_adapter)
        firestoreRoot = Firebase.firestore

        // SESSION MANAGER
        loginSession = SessionManager(this, SessionManager.LOGIN_SESSION)
        userIdSession = loginSession.getDataFromLoginSession().get(SessionManager.KEY_NOHP).toString()

        cartSession = SessionManager(this, SessionManager.CART_SESSION)

        showRecyclerKueMarmer()

        ivBack.setOnClickListener(this)

    }

    private fun showRecyclerKueMarmer() {
        rvActivity.layoutManager = LinearLayoutManager(this)

        val query = firestoreRoot.collection("cakes")
        val options = FirestoreRecyclerOptions.Builder<KueMarmerModel>()
            .setQuery(query, KueMarmerModel::class.java)
            .build()

        // CEK CART
        firestoreRoot.collection("carts/CART_$userIdSession/items")
            .addSnapshotListener(this, object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        return
                    }

                    if (!value!!.isEmpty) {
                        val cartList = arrayListOf<CartModel>()
                        for (ds in value) {
                            val cartModel = CartModel()
                            cartModel.id = ds.id
                            cartModel.qty = ds.get("qty").toString()
                            cartList.add(cartModel)
                        }

                        val adapter = KueMarmerViewsAdapter(options, this@ViewsAdapterActivity, cartList)
                        rvActivity.adapter = adapter

                        getAdapterListener.getAdapter(adapter)

                    }

                    else {
                        val cartList = arrayListOf<CartModel>()
                        val adapter = KueMarmerViewsAdapter(options, this@ViewsAdapterActivity, cartList)
                        rvActivity.adapter = adapter

                        getAdapterListener.getAdapter(adapter)
                    }
                }

            })
    }


    interface GetAdapter {
        fun getAdapter(adapter: KueMarmerViewsAdapter)
    }

    fun setGetAdapterListener(listener: GetAdapter) {
        this.getAdapterListener = listener
    }

    override fun onStart() {
        super.onStart()
        setGetAdapterListener(object : GetAdapter {
            override fun getAdapter(adapter: KueMarmerViewsAdapter) {
                adapter.startListening()
            }
        })
    }

    override fun onStop() {
        super.onStop()
        setGetAdapterListener(object : GetAdapter {
            override fun getAdapter(adapter: KueMarmerViewsAdapter) {
                adapter.startListening()
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

    override fun onKueMarmerClicked(data: KueMarmerModel) {
        val intent = Intent(this, DetailKueMarmerActivity::class.java)
        intent.putExtra("clickedKueMarmer", data)
        startActivity(intent)
        finish()
    }
}