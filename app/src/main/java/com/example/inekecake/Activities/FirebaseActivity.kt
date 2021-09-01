package com.example.inekecake.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.inekecake.Model.FirebaseModel
import com.example.inekecake.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var rootNode: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var btnSend: Button
    private lateinit var etFbNama: EditText
    private lateinit var etFbEmail: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase)
        supportActionBar?.title = "Firebase"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        etFbNama = findViewById(R.id.et_fb_nama)
        etFbEmail = findViewById(R.id.et_fb_email)
        btnSend = findViewById(R.id.btn_firebase)
        btnSend.setOnClickListener(this)



    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_firebase -> {
//
//                val nama = etFbNama.text.toString().trim()
//                val email = etFbEmail.text.toString().trim()
//                val data = FirebaseModel(nama, email)
//
//                rootNode = Firebase.database("https://ineke-cake-default-rtdb.asia-southeast1.firebasedatabase.app/")
//                reference = rootNode.getReference("customers")
//                reference.child("$nama-0001").setValue(data)
//
//                Toast.makeText(this, "Terpencet", Toast.LENGTH_SHORT).show()

            }
        }
    }
}