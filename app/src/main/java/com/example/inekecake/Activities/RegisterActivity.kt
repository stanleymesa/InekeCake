package com.example.inekecake.Activities

import android.app.ActivityOptions
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.inekecake.Model.FirebaseModel
import com.example.inekecake.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.*
import com.google.firebase.database.Query
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var logoAtRegister: ImageView
    private lateinit var sloganAtRegister: TextView
    private lateinit var firstnameAtRegister: TextInputLayout
    private lateinit var lastnameAtRegister: TextInputLayout
    private lateinit var emailAtRegister: TextInputLayout
    private lateinit var noHpAtRegister: TextInputLayout
    private lateinit var passwordAtRegister: TextInputLayout
    private lateinit var alamatAtRegister: TextInputLayout
    private lateinit var kotaAtRegister: TextInputLayout
    private lateinit var kodeposAtRegister: TextInputLayout
    private lateinit var btnRegister: Button
    private lateinit var btnLogin: Button
    private lateinit var firebaseURL: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var firestoreRoot: FirebaseFirestore
    private lateinit var firstname: String
    private lateinit var lastname: String
    private lateinit var email: String
    private lateinit var noHp: String
    private lateinit var password: String
    private lateinit var alamat: String
    private lateinit var kota: String
    private lateinit var kodepos: String

    override fun onCreate(savedInstanceState: Bundle?) {
        // set no dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        logoAtRegister = findViewById(R.id.logo_inekecake_register)
        sloganAtRegister = findViewById(R.id.slogan_inekecake_register)
        firstnameAtRegister = findViewById(R.id.et_firstname_register)
        lastnameAtRegister = findViewById(R.id.et_lastname_register)
        emailAtRegister = findViewById(R.id.et_email_register)
        noHpAtRegister = findViewById(R.id.et_noHp_register)
        passwordAtRegister = findViewById(R.id.et_password_register)
        alamatAtRegister = findViewById(R.id.et_alamat_register)
        kotaAtRegister = findViewById(R.id.et_kota_register)
        kodeposAtRegister = findViewById(R.id.et_kodepos_register)
        btnLogin = findViewById(R.id.btn_login_at_register)
        btnRegister = findViewById(R.id.btn_register_at_register)

        // SET FIREBASE
        firebaseURL = FirebaseDatabase.getInstance("https://ineke-cake-default-rtdb.asia-southeast1.firebasedatabase.app/")
        reference = firebaseURL.getReference("users")
        firestoreRoot = Firebase.firestore

        // EMD SET

        btnLogin.setOnClickListener(this)
        btnRegister.setOnClickListener(this)
    }

    private fun validateFirstname() {
        firstname = firstnameAtRegister.editText?.text.toString().trim()

        if (firstname.isEmpty()) {
            firstnameAtRegister.error = "First Name tidak boleh kosong"
        } else {
            firstnameAtRegister.error = null
        }
    }

    private fun validateLastname(){
        lastname = lastnameAtRegister.editText?.text.toString().trim()

        if (lastname.isEmpty()) {
            lastnameAtRegister.error = "Last Name tidak boleh kosong"
        } else {
            lastnameAtRegister.error = null
        }
    }

    private fun validateEmail(isIntent: Boolean) {
        email = emailAtRegister.editText?.text.toString()
        val valid = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")

        if (email.isEmpty()) {
            emailAtRegister.error = "Email tidak boleh kosong"
        }else if (!valid.matches(email)) {
            emailAtRegister.error = "Email tidak sesuai (contoh@email.com)"
        } else {
            val queryEmail = firestoreRoot.collection("users").whereEqualTo("email", email)
            queryEmail.addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        Log.w(TAG, "Listen Failed", error)
                    }

                    if (!value!!.isEmpty) {
                        emailAtRegister.error = "Email telah digunakan"
                    } else {
                        emailAtRegister.error = null
                        if (isIntent) {
                            val dataUser = ArrayList<FirebaseModel>()
                            val firebaseModel = FirebaseModel(
                                firstname, lastname, email, noHp, password
                            )
                            dataUser.add(firebaseModel)
                            val intent = Intent(this@RegisterActivity, VerifyOtpActivity::class.java)
                            intent.putExtra("dataUser", dataUser)
                            intent.putExtra("from", "register")

                            val pairs = ArrayList<android.util.Pair<View, String>>()
                            pairs.add(android.util.Pair(logoAtRegister, "logo"))
                            pairs.add(android.util.Pair(sloganAtRegister, "slogan"))
                            pairs.add(android.util.Pair(noHpAtRegister, "noHp"))
                            pairs.add(android.util.Pair(passwordAtRegister, "password"))
                            pairs.add(android.util.Pair(btnRegister, "button1"))
                            pairs.add(android.util.Pair(btnLogin, "button2"))

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                val options = ActivityOptions.makeSceneTransitionAnimation(
                                    this@RegisterActivity,
                                    pairs[0],
                                    pairs[1],
                                    pairs[2],
                                    pairs[3],
                                    pairs[4],
                                    pairs[5]
                                )
                                startActivity(intent, options.toBundle())
                            } else {
                                startActivity(intent)
                            }
                            finish()
                        }
                    }
                }

            })
        }
    }

    private fun validateNoHp() {
        val noHpAwal = noHpAtRegister.editText?.text.toString().trim()
        if (noHpAwal.isEmpty()) {
            noHpAtRegister.error = "Nomor handphone tidak boleh kosong"
            allValidation()
        } else {
            if (noHpAwal.get(0).toString().equals("0")) {
                noHp = "+62" + noHpAwal.drop(1)
            } else {
                noHp = "+62" + noHpAwal
            }
            val query = firestoreRoot.collection("users").whereEqualTo("noHp", noHp)
            query.addSnapshotListener(this, object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        Log.w(TAG, "Listen Failed", error)
                    }

                    if (!value!!.isEmpty) {
                        noHpAtRegister.error = "Nomor Handphone telah digunakan"
                        allValidation()
                    } else {
                        noHpAtRegister.error = null

                        if (allValidation()) {
                            validateEmail(true)
                        }

                    }
                }

            })
        }
    }

    private fun validatePassword() {
        password = passwordAtRegister.editText?.text.toString()
        val valid = Regex("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}\$")

        if (password.isEmpty()) {
            passwordAtRegister.error = "Password tidak boleh kosong"
        }else if (!valid.matches(password)) {
            passwordAtRegister.error = "Password lemah!\n(minimal 8 karakter, gunakan 1 huruf besar dan angka)"
        } else {
            passwordAtRegister.error = null
        }
    }

    private fun validateAlamat(){
        alamat = alamatAtRegister.editText?.text.toString().trim()

        if (alamat.isEmpty()) {
            alamatAtRegister.error = "Alamat tidak boleh kosong"
        } else {
            alamatAtRegister.error = null
        }
    }

    private fun validateKota(){
        kota = kotaAtRegister.editText?.text.toString().trim()

        if (kota.isEmpty()) {
            kotaAtRegister.error = "Kota tidak boleh kosong"
        } else {
            kotaAtRegister.error = null
        }
    }

    private fun validateKodepos(){
        kodepos = kodeposAtRegister.editText?.text.toString().trim()

        if (kodepos.isEmpty()) {
            kodeposAtRegister.error = "Kode pos tidak boleh kosong"
        } else {
            kodeposAtRegister.error = null
        }
    }

    private fun allValidation(): Boolean {
        validateFirstname()
        validateLastname()
        validatePassword()
        validateEmail(false)
        validateAlamat()
        validateKota()
        validateKodepos()

        if (firstnameAtRegister.error != null ||
            lastnameAtRegister.error != null ||
            passwordAtRegister.error != null ||
            emailAtRegister.error != null ||
            alamatAtRegister.error != null ||
            kotaAtRegister.error != null ||
            kodeposAtRegister.error != null){

            return false
        }
        return true
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login_at_register -> {
                val pairs = ArrayList<android.util.Pair<View, String>>()
                pairs.add(android.util.Pair(logoAtRegister, "logo"))
                pairs.add(android.util.Pair(sloganAtRegister, "slogan"))
                pairs.add(android.util.Pair(noHpAtRegister, "noHp"))
                pairs.add(android.util.Pair(passwordAtRegister, "password"))
                pairs.add(android.util.Pair(btnRegister, "button1"))
                pairs.add(android.util.Pair(btnLogin, "button2"))


                val intent = Intent(this, LoginActivity::class.java)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    val options = ActivityOptions.makeSceneTransitionAnimation(
                        this,
                        pairs[0],
                        pairs[1],
                        pairs[2],
                        pairs[3],
                        pairs[4],
                        pairs[5]
                    )
                    startActivity(intent, options.toBundle())
                } else {
                    startActivity(intent)
                }
                finish()
            }

            R.id.btn_register_at_register -> {
                validateNoHp()
            }
        }
    }


    override fun onBackPressed() {
        val pairs = ArrayList<android.util.Pair<View, String>>()
        pairs.add(android.util.Pair(logoAtRegister, "logo"))
        pairs.add(android.util.Pair(sloganAtRegister, "slogan"))
        pairs.add(android.util.Pair(noHpAtRegister, "noHp"))
        pairs.add(android.util.Pair(passwordAtRegister, "password"))
        pairs.add(android.util.Pair(btnLogin, "login"))
        pairs.add(android.util.Pair(btnRegister, "register"))

        val intent = Intent(this, LoginActivity::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val options = ActivityOptions.makeSceneTransitionAnimation(this, pairs[0], pairs[1], pairs[2], pairs[3], pairs[4], pairs[5])
            startActivity(intent, options.toBundle())
        } else {
            startActivity(intent)
        }
        finish()
    }
}