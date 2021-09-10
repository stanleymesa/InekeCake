package com.example.inekecake.Activities

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Fade
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import com.example.inekecake.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.*
import okhttp3.internal.cache.DiskLruCache

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity(),
    View.OnClickListener{

    private lateinit var logoAtLogin: ImageView
    private lateinit var sloganAtLogin: TextView
    private lateinit var noHpAtLogin: TextInputLayout
    private lateinit var passwordAtLogin: TextInputLayout
    private lateinit var btnRegister: Button
    private lateinit var btnLogin: Button
    private lateinit var noHp: String
    private lateinit var password: String
    private lateinit var firebaseURL: FirebaseDatabase
    private lateinit var reference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        // set no dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        supportActionBar?.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        logoAtLogin = findViewById(R.id.logo_inekecake_login)
        sloganAtLogin = findViewById(R.id.slogan_inekecake_login)
        noHpAtLogin = findViewById(R.id.et_noHp_login)
        passwordAtLogin = findViewById(R.id.et_password_login)
        btnLogin = findViewById(R.id.btn_login_at_login)
        btnRegister = findViewById(R.id.btn_register_at_login)

        firebaseURL = FirebaseDatabase.getInstance("https://ineke-cake-default-rtdb.asia-southeast1.firebasedatabase.app/")
        reference = firebaseURL.getReference("users")

        btnLogin.setOnClickListener(this)
        btnRegister.setOnClickListener(this)

    }

    private fun validatePassword(): Boolean {
        password = passwordAtLogin.editText?.text.toString()
        val valid = Regex("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}\$")

        if (password.isEmpty()) {
            passwordAtLogin.error = "Password tidak boleh kosong"
            return false
        }else if (!valid.matches(password)) {
            passwordAtLogin.error = "Password lemah!\n(minimal 8 karakter, gunakan 1 huruf besar dan angka)"
            return false
        } else {
            passwordAtLogin.error = null
            return true
        }
    }

    private fun validateNoHp() {
        val noHpAwal = noHpAtLogin.editText?.text.toString().trim()
        if (noHpAwal.isEmpty()) {
            noHpAtLogin.error = "Nomor handphone tidak boleh kosong"
            validatePassword()
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
                        Toast.makeText(this@LoginActivity, "NOMOR HP ADA!", Toast.LENGTH_SHORT).show()
                        noHpAtLogin.error = null

                        if (validatePassword()) {

                            val passwordFromDB = snapshot.child("stanley").child("password").getValue().toString()
                            if (password.equals(passwordFromDB)) {
                                Toast.makeText(this@LoginActivity, "Berhasil Log In!", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                passwordAtLogin.error = "Password anda salah!"
                            }

                        }

                    } else {
                        noHpAtLogin.error = "No. Handphone tidak ditemukan!"
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@LoginActivity, error.message, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_login_at_login -> {
                validateNoHp()
            }

            R.id.btn_register_at_login -> {
                var pairs = ArrayList<android.util.Pair<View, String>>()
                pairs.add(android.util.Pair(logoAtLogin, "logo"))
                pairs.add(android.util.Pair(sloganAtLogin, "slogan"))
                pairs.add(android.util.Pair(noHpAtLogin, "noHp"))
                pairs.add(android.util.Pair(passwordAtLogin, "password"))
                pairs.add(android.util.Pair(btnLogin, "button1"))
                pairs.add(android.util.Pair(btnRegister, "button2"))

                val intent = Intent(this, RegisterActivity::class.java)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    val options = ActivityOptions.makeSceneTransitionAnimation(
                        this, pairs[0], pairs[1], pairs[2], pairs[3], pairs[4], pairs[5])

                    startActivity(intent, options.toBundle())
                } else {
                    startActivity(intent)
                }
                finish()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

}