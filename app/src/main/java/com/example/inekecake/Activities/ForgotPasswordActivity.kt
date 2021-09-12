package com.example.inekecake.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.inekecake.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.*

class ForgotPasswordActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var etNoHpForgot: TextInputLayout
    private lateinit var btnNextForgot: Button
    private lateinit var noHp: String
    private lateinit var firebaseURL: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        // set no dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        supportActionBar?.hide()

        etNoHpForgot = findViewById(R.id.et_noHp_forgot)
        btnNextForgot = findViewById(R.id.btn_next_forgot)

        firebaseURL = FirebaseDatabase.getInstance("https://ineke-cake-default-rtdb.asia-southeast1.firebasedatabase.app/")
        reference = firebaseURL.getReference("users")

        btnNextForgot.setOnClickListener(this)
    }

    private fun validateNoHp() {
        val noHpAwal = etNoHpForgot.editText?.text.toString().trim()
        if (noHpAwal.isEmpty()) {
            etNoHpForgot.error = "Nomor handphone tidak boleh kosong"
        } else {
            if (noHpAwal.get(0).toString().equals("0")) {
                noHp = "+62" + noHpAwal.drop(1)
            } else {
                noHp = "+62" + noHpAwal
            }

            val query: Query = reference.orderByChild("noHp").equalTo(noHp)
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        etNoHpForgot.error = null
                        val intent = Intent(this@ForgotPasswordActivity, VerifyOtpActivity::class.java)
                        intent.putExtra("noHp", noHp)
                        intent.putExtra("from", "forgotPassword")
                        startActivity(intent)
                        finish()

                    } else {
                        etNoHpForgot.error = "No. Handphone tidak ditemukan!"
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@ForgotPasswordActivity, error.message, Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@ForgotPasswordActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            })

        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_next_forgot -> {
                validateNoHp()
            }
        }
    }
}