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
import com.example.inekecake.Model.FirebaseModel
import com.example.inekecake.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
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

        btnLogin.setOnClickListener(this)
        btnRegister.setOnClickListener(this)
    }

    private fun validateFullName(): Boolean {
        fullname = fullnameAtRegister.editText?.text.toString().trim()

        if (fullname.isEmpty()) {
            fullnameAtRegister.error = "Fullname tidak boleh kosong"
            return false
        } else {
            fullnameAtRegister.error = null
            return true
        }
    }

    private fun validateUsername(): Boolean {
        username = usernameAtRegister.editText?.text.toString()
        val valid = Regex("\\A\\w{4,20}\\z")

        if (username.isEmpty()) {
            usernameAtRegister.error = "Username tidak boleh kosong"
            return false
        }else if (!valid.matches(username)) {
            usernameAtRegister.error = "Username tidak boleh ada spasi"
            return false
        } else {
            usernameAtRegister.isErrorEnabled = false
            return true

        }
    }

    private fun validateEmail(): Boolean {
        email = emailAtRegister.editText?.text.toString()
        val valid = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")

        if (email.isEmpty()) {
            emailAtRegister.error = "Email tidak boleh kosong"
            return false
        }else if (!valid.matches(email)) {
            emailAtRegister.error = "Email tidak sesuai (contoh@email.com)"
            return false
        } else {
            emailAtRegister.isErrorEnabled = false
            return true

        }
    }

    private fun validateNoHp(): Boolean {
        noHp = noHpAtRegister.editText?.text.toString().trim()

        if (noHp.isEmpty()) {
            noHpAtRegister.error = "Nomor handphone tidak boleh kosong"
            return false
        } else {
            noHpAtRegister.error = null
            return true
        }
    }

    private fun validatePassword(): Boolean {
        password = passwordAtRegister.editText?.text.toString()
        val valid = Regex(".*[A-Z0-9][A-Z0-9].*")

        if (password.isEmpty()) {
            passwordAtRegister.error = "Password tidak boleh kosong"
            return false
        }else if (!valid.matches(password)) {
            passwordAtRegister.error = "Password lemah! (minimal gunakan 1 huruf besar dan angka)"
            return false
        } else {
            passwordAtRegister.isErrorEnabled = false
            return true

        }
    }

    private fun allValidation(): Boolean {
        var bool = true
        if (!validateFullName()) {
            bool = false
        }
        if (!validateUsername()) {
            bool = false
        }
        if (!validateEmail()) {
            bool = false
        }
        if (!validateNoHp()) {
            bool = false
        }
        if (!validatePassword()) {
            bool = false
        }
        return bool
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_login_at_register -> {
                val pairs = ArrayList<android.util.Pair<View, String>>()
                pairs.add(android.util.Pair(logoAtRegister, "logo"))
                pairs.add(android.util.Pair(sloganAtRegister, "slogan"))
                pairs.add(android.util.Pair(usernameAtRegister, "username"))
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
            }

            R.id.btn_register_at_register -> {

                if (!allValidation()) {
                    return
                }

                // simpan user ke firebase
                firebaseURL = Firebase.database("https://ineke-cake-default-rtdb.asia-southeast1.firebasedatabase.app/")
                reference = firebaseURL.getReference("users")
                val users = FirebaseModel(fullname, username, email, noHp, password)
                reference.child(username).setValue(users)
                Toast.makeText(this, "Akun anda berhasil didaftarkan!", Toast.LENGTH_SHORT).show()

                // set animasi ke login
                val pairs = ArrayList<android.util.Pair<View, String>>()
                pairs.add(android.util.Pair(logoAtRegister, "logo"))
                pairs.add(android.util.Pair(sloganAtRegister, "slogan"))
                pairs.add(android.util.Pair(usernameAtRegister, "username"))
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


            }
        }
    }

    override fun onBackPressed() {
        val pairs = ArrayList<android.util.Pair<View, String>>()
        pairs.add(android.util.Pair(logoAtRegister, "logo"))
        pairs.add(android.util.Pair(sloganAtRegister, "slogan"))
        pairs.add(android.util.Pair(usernameAtRegister, "username"))
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
    }
}