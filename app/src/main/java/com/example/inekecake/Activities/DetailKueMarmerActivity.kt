package com.example.inekecake.Activities

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.inekecake.Model.KueMarmerModel
import com.example.inekecake.R
import com.example.inekecake.Session.SessionManager
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.NumberFormat
import java.util.*

class DetailKueMarmerActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var ivKueMarmer: ImageView
    private lateinit var ivPlus: ImageView
    private lateinit var ivMinus: ImageView
    private lateinit var tvJumlahItem: TextView
    private lateinit var tvNamaKueMarmer: TextView
    private lateinit var tvHargaKueMarmer: TextView
    private lateinit var firestoreRoot: FirebaseFirestore
    private lateinit var dataFromIntent: KueMarmerModel
    private lateinit var btnAddToCart: RelativeLayout
    private lateinit var sessionManager: SessionManager
    private lateinit var userIdSession: String
    private lateinit var getDataFromDBListener: GetDataFromDB
    private lateinit var dataFromDB: KueMarmerModel
    private var itemCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_kue_marmer)

        dataFromIntent = intent.getParcelableExtra("clickedKueMarmer")!!

        ivKueMarmer = findViewById(R.id.iv_detail_kuemarmer)
        tvNamaKueMarmer = findViewById(R.id.tv_nama_detail_kuemarmer)
        tvHargaKueMarmer = findViewById(R.id.tv_harga_detail_kuemarmer)
        ivPlus = findViewById(R.id.iv_plus_detail_kuemarmer)
        ivMinus = findViewById(R.id.iv_minus_detail_kuemarmer)
        tvJumlahItem = findViewById(R.id.tv_jumlahitem_detail_kuemarmer)
        btnAddToCart = findViewById(R.id.dummybtn_addtocart_detail_kuemarmer)

        // SESSION MANAGER
        sessionManager = SessionManager(this, SessionManager.LOGIN_SESSION)
        userIdSession = sessionManager.getDataFromLoginSession().get(SessionManager.KEY_NOHP).toString()

        // FIREBASE
        firestoreRoot = Firebase.firestore

        // GET DATA FROM DB
        setGetDataFromDBListener(object : GetDataFromDB {
            override fun getData(data: KueMarmerModel) {
                dataFromDB = data
            }
        })


        setJumlahItem()
        setDetails()

        // CLICKED
        ivPlus.setOnClickListener(this)
        ivMinus.setOnClickListener(this)
        btnAddToCart.setOnClickListener(this)
    }

    private fun setDetails() {

        firestoreRoot.document("cakes/${dataFromIntent.id}")
            .addSnapshotListener(this, object : EventListener<DocumentSnapshot> {
                override fun onEvent(value: DocumentSnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        return
                    }

                    if (value!!.exists()) {

                        getDataFromDBListener.getData(value.toObject(KueMarmerModel::class.java)!!)

                        tvNamaKueMarmer.text = value.get("nama").toString()
                        tvHargaKueMarmer.text = value.get("harga").toString()
                        Glide.with(this@DetailKueMarmerActivity)
                            .load(value.get("url"))
                            .into(ivKueMarmer)
                    }
                }

            })
    }


    private fun setJumlahItem() {
        tvJumlahItem.text = itemCount.toString()
    }

    private fun addToCart() {
        val setDataToCart = hashMapOf("qty" to tvJumlahItem.text.toString())
        val setUserIdToCart = hashMapOf("id_user" to userIdSession)
        firestoreRoot.document("carts/CART_$userIdSession/items/${dataFromDB.id}").set(setDataToCart)
        firestoreRoot.document("carts/CART_$userIdSession").set(setUserIdToCart)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.iv_plus_detail_kuemarmer -> {
                itemCount++
                setJumlahItem()
            }

            R.id.iv_minus_detail_kuemarmer -> {
                if (itemCount == 0) {
                    return
                }
                itemCount--
                setJumlahItem()
            }

            R.id.dummybtn_addtocart_detail_kuemarmer -> {
                addToCart()
            }
        }
    }

    private fun setGetDataFromDBListener(listener: GetDataFromDB) {
        this.getDataFromDBListener = listener
    }

    interface GetDataFromDB {
        fun getData(data: KueMarmerModel)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, ViewsAdapterActivity::class.java))
        finish()
    }
}