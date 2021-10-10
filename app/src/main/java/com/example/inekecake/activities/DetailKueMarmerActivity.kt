package com.example.inekecake.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.example.inekecake.model.KueMarmerModel
import com.example.inekecake.R
import com.example.inekecake.`object`.ButtonDetails
import com.example.inekecake.session.SessionManager
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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
    private lateinit var layoutItemCount: LinearLayout
    private lateinit var tvBtnAddToCart: TextView
    private lateinit var sessionManager: SessionManager
    private lateinit var userIdSession: String
    private lateinit var getDataFromDBListener: GetDataFromDB
    private lateinit var dataFromDB: KueMarmerModel
    private var itemCount = MutableLiveData(1)

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
        tvBtnAddToCart = findViewById(R.id.tv_dummybtn_addtocart_detail_kuemarmer)
        layoutItemCount = findViewById(R.id.layout_itemcount_detail_kuemarmer)

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
            .addSnapshotListener(this, EventListener { value, error ->
                if (error != null) {
                    return@EventListener
                }

                if (value!!.exists()) {

                    getDataFromDBListener.getData(value.toObject(KueMarmerModel::class.java)!!)

                    tvNamaKueMarmer.text = value.get("nama").toString()
                    tvHargaKueMarmer.text = value.get("harga").toString()
                    Glide.with(this@DetailKueMarmerActivity)
                        .load(value.get("url"))
                        .into(ivKueMarmer)

                    setButtonDetails(value.get("is_available").toString().toBoolean())

                }
            })

    }

    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    private fun setButtonDetails(is_available: Boolean) {
        // JIKA STOK HABIS

        if (!is_available) {
            btnAddToCart.background = getDrawable(R.drawable.round_gray)
            tvBtnAddToCart.text = ButtonDetails.STOK_HABIS
            layoutItemCount.visibility = View.GONE
            return
        }

        // JIKA STOK ADA

        btnAddToCart.background = getDrawable(R.drawable.round_softpink)
        layoutItemCount.visibility = View.VISIBLE

        firestoreRoot.collection("carts/CART_$userIdSession/items").whereEqualTo(FieldPath.documentId(), dataFromIntent.id)
            .addSnapshotListener(this, EventListener { value, error ->
                if (error != null) {
                    return@EventListener
                }

                // JIKA BARANG ADA DI CART
                if (!value!!.isEmpty) {

                    var qty = 1
                    for (ds in value) {
                        qty = ds.get("qty").toString().toInt()
                    }
                    itemCount.value = qty
                    setJumlahItem()

                    itemCount.observe(this, androidx.lifecycle.Observer {
                        if (it.equals(0)) {
                            btnAddToCart.background = getDrawable(R.drawable.round_pink)
                            tvBtnAddToCart.text = ButtonDetails.DELETE_FROM_CART
                        } else {
                            btnAddToCart.background = getDrawable(R.drawable.round_softpink)
                            tvBtnAddToCart.text = "${ButtonDetails.UPDATE_CART} ~ ${dataFromIntent.harga.toInt() * it}"
                        }
                    })

                }

                // JIKA BARANG TIDAK ADA DI CART
                else {

                    itemCount.value = 1
                    setJumlahItem()

                    itemCount.observe(this, androidx.lifecycle.Observer {
                        if (it.equals(0)) {
                            btnAddToCart.background = getDrawable(R.drawable.round_softorange)
                            tvBtnAddToCart.text = ButtonDetails.BACK_TO_MENU
                        } else {
                            btnAddToCart.background = getDrawable(R.drawable.round_softpink)
                            tvBtnAddToCart.text = "${ButtonDetails.ADD_TO_CART} ~ ${dataFromIntent.harga.toInt() * it}"
                        }
                    })

                }

            })
    }


    private fun setJumlahItem() {
        tvJumlahItem.text = itemCount.value.toString()
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
                itemCount.value = itemCount.value!!.plus(1)
                setJumlahItem()
            }

            R.id.iv_minus_detail_kuemarmer -> {
                if (itemCount.value!! == 0) {
                    return
                }
                itemCount.value = itemCount.value!!.minus(1)
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