package com.example.inekecake.Activities

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var logoAtRegister: ImageView
    private lateinit var sloganAtRegister: TextView
    private lateinit var fullnameAtRegister: TextInputLayout
    private lateinit var usernameAtRegister: TextInputLayout
    private lateinit var emailAtRegister: TextInputLayout
    private lateinit var noHpAtRegister: TextInputLayout
    private lateinit var passwordAtRegister: TextInputLayout
    private lateinit var btnRegister: Button
    private lateinit var btnLogin: Button
    private lateinit var firebaseURL: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var fullname: String
    private lateinit var username: String
    private lateinit var email: String
    private lateinit var noHp: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        // set no dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        logoAtRegister = findViewById(R.id.logo_inekecake_register)
        sloganAtRegister = findViewById(R.id.slogan_inekecake_register)
        fullnameAtRegister = findViewById(R.id.et_fullname_register)
        usernameAtRegister = findViewById(R.id.et_username_register)
        emailAtRegister = findViewById(R.id.et_email_register)
        noHpAtRegister = findViewById(R.id.et_noHp_register)
        passwordAtRegister = findViewById(R.id.et_password_register)
        btnLogin = findViewById(R.id.btn_login_at_register)
        btnRegister = findViewById(R.id.btn_register_at_register)

        firebaseURL = FirebaseDatabase.getInstance("https://ineke-cake-default-rtdb.asia-southeast1.firebasedatabase.app/")
        reference = firebaseURL.getReference("users")

        btnLogin.setOnClickListener(this)
        btnRegister.setOnClickListener(this)
    }

    private fun validateFullName() {
        fullname = fullnameAtRegister.editText?.text.toString().trim()

        if (fullname.isEmpty()) {
            fullnameAtRegister.error = "Fullname tidak boleh kosong"
        } else {
            fullnameAtRegister.error = null
        }
    }

    private fun validateUsername(){
        username = usernameAtRegister.editText?.text.toString()
        val valid = Regex("\\A\\w{4,20}\\z")

        if (username.isEmpty()) {
            usernameAtRegister.error = "Username tidak boleh kosong"
        }else if (!valid.matches(username)) {
            usernameAtRegister.error = "Username tidak boleh ada spasi"
        } else {
            usernameAtRegister.error = null
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
            emailAtRegister.error = null
            val queryEmail: Query = reference.orderByChild("email").equalTo(email)
            queryEmail.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        emailAtRegister.error = "Email sudah terpakai"
                    } else {
                        emailAtRegister.error = null
                        if (isIntent) {
                            val dataUser = ArrayList<FirebaseModel>()
                            val firebaseModel = FirebaseModel(
                                fullname, username, email, noHp, password
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

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }
    }

    private fun validateNoHp() {
        val noHpAwal = noHpAtRegister.editText?.text.toString().trim()
        if (noHpAwal.isEmpty()) {
            noHpAtRegister.error = "Nomor handphone tidak boleh kosong"
            allValidation(false)
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
                        noHpAtRegister.error = "Nomor Handphone sudah terpakai"
                        allValidation(false)
                    } else {
                        noHpAtRegister.error = null
                        if (allValidation(false)) {
                            validateEmail(true)
                        }

                    }
                }

                override fun onCancelled(error: DatabaseError) {

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

    private fun allValidation(isIntent: Boolean): Boolean {
        validateFullName()
        validateUsername()
        validatePassword()
        validateEmail(isIntent)

        if (fullnameAtRegister.error != null ||
            usernameAtRegister.error != null ||
            passwordAtRegister.error != null ||
                emailAtRegister.error != null){

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