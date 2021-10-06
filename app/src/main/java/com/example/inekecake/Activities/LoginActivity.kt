package com.example.inekecake.Activities

import android.app.Activity
import android.app.ActivityOptions
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.transition.Fade
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import com.example.inekecake.Model.FirebaseModel
import com.example.inekecake.R
import com.example.inekecake.Session.SessionManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.*
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity(),
    View.OnClickListener{

    private lateinit var logoAtLogin: ImageView
    private lateinit var sloganAtLogin: TextView
    private lateinit var noHpAtLogin: TextInputLayout
    private lateinit var passwordAtLogin: TextInputLayout
    private lateinit var etNoHpAtLogin: TextInputEditText
    private lateinit var etPasswordAtLogin: TextInputEditText
    private lateinit var btnRegister: Button
    private lateinit var btnLogin: Button
    private lateinit var btnForgotPassword: Button
    private lateinit var checkboxRememberMe: CheckBox
    private lateinit var noHp: String
    private lateinit var password: String
    private lateinit var firestoreRoot: FirebaseFirestore
    private lateinit var loginSession: SessionManager
    private lateinit var rememberMeSession: SessionManager



    override fun onCreate(savedInstanceState: Bundle?) {
        // set no dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        logoAtLogin = findViewById(R.id.logo_inekecake_login)
        sloganAtLogin = findViewById(R.id.slogan_inekecake_login)
        noHpAtLogin = findViewById(R.id.ti_noHp_login)
        passwordAtLogin = findViewById(R.id.ti_password_login)
        etNoHpAtLogin = findViewById(R.id.et_noHp_login)
        etPasswordAtLogin = findViewById(R.id.et_password_login)
        btnLogin = findViewById(R.id.btn_login_at_login)
        btnRegister = findViewById(R.id.btn_register_at_login)
        btnForgotPassword = findViewById(R.id.btn_lupa_password)
        checkboxRememberMe = findViewById(R.id.checkbox_rememberme)

        // SET FIREBASE
        firestoreRoot = Firebase.firestore
        // END SET

        btnLogin.setOnClickListener(this)
        btnRegister.setOnClickListener(this)
        btnForgotPassword.setOnClickListener(this)

        // SET SESSION
        loginSession = SessionManager(this, SessionManager.LOGIN_SESSION)
        rememberMeSession = SessionManager(this, SessionManager.REMEMBERME_SESSION)
        setEditText()

    }


    private fun setEditText() {
        if (loginSession.isLoggedIn()) {
            val dataUser = loginSession.getDataFromLoginSession()
            val noHp = dataUser.get(SessionManager.KEY_NOHP)!!.replace("+62", "0")
            val password = dataUser.get(SessionManager.KEY_PASSWORD)

            etNoHpAtLogin.setText(noHp)
            etPasswordAtLogin.setText(password)
        }
    }

    private fun goToRegister() {
        var pairs = ArrayList<android.util.Pair<View, String>>()
        pairs.add(android.util.Pair(logoAtLogin, "logo"))
        pairs.add(android.util.Pair(sloganAtLogin, "slogan"))
        pairs.add(android.util.Pair(noHpAtLogin, "noHp"))
        pairs.add(android.util.Pair(passwordAtLogin, "password"))
        pairs.add(android.util.Pair(btnLogin, "button1"))
        pairs.add(android.util.Pair(btnRegister, "button2"))

        val intent = Intent(this, RegisterActivity::class.java)

        val options = ActivityOptions.makeSceneTransitionAnimation(
            this, pairs[0], pairs[1], pairs[2], pairs[3], pairs[4], pairs[5])
        startActivity(intent, options.toBundle())

    }

    private fun goToForgotPassword() {
        var pairs = ArrayList<android.util.Pair<View, String>>()
        pairs.add(android.util.Pair(logoAtLogin, "logo"))
        pairs.add(android.util.Pair(sloganAtLogin, "slogan"))
        pairs.add(android.util.Pair(noHpAtLogin, "noHp"))
        pairs.add(android.util.Pair(btnLogin, "button1"))

        val intent = Intent(this, ForgotPasswordActivity::class.java)

        val options = ActivityOptions.makeSceneTransitionAnimation(
            this, pairs[0], pairs[1], pairs[2], pairs[3])
        startActivity(intent, options.toBundle())

    }

    private fun isInternetConnected(ctx: LoginActivity): Boolean {
        val connectivityManager: ConnectivityManager = ctx.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkWifi: NetworkInfo? = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val networkMobile: NetworkInfo? = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)

        if ( (networkWifi != null && networkWifi.isConnected) || (networkMobile != null && networkMobile.isConnected) ) {
            return true
        }
        return false
    }

    private fun alertDialogConnection() {
        val alertDialogBuilder = MaterialAlertDialogBuilder(this)
            .setTitle("Connection Problem")
            .setMessage("Tidak ada koneksi internet\nMohon aktifkan internet anda")
            .setIcon(R.drawable.ic_baseline_wifi_off_24)
            .setCancelable(false)
            .setPositiveButton("Back", object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                }
            })

        alertDialogBuilder.show()
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

            val query = firestoreRoot.collection("users").whereEqualTo(FieldPath.documentId(), noHp)
            query.addSnapshotListener(this, object : EventListener<QuerySnapshot>{
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        Toast.makeText(this@LoginActivity, error.message, Toast.LENGTH_SHORT).show()
                    }

                    if (!value!!.isEmpty){
                        if (validatePassword()) {
                            var dataUser = FirebaseModel()
                            for (snapshot in value){
                                dataUser = snapshot.toObject()
                            }

                            if (password.equals(dataUser.password)) {
                                // SHARED LOGIN
                                loginSession.createLogin(
                                    dataUser.firstname,
                                    dataUser.lastname,
                                    dataUser.email,
                                    dataUser.noHp,
                                    dataUser.password,
                                    dataUser.alamat,
                                    dataUser.kota,
                                    dataUser.kodepos,
                                    dataUser.tgl_register
                                )
                                if (checkboxRememberMe.isChecked) {
                                    rememberMeSession.createRememberMe()
                                }
                                Toast.makeText(this@LoginActivity, "Berhasil Log In!", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                passwordAtLogin.error = "Password anda salah!"
                            }

                        }

                    } else{
                        noHpAtLogin.error = "Nomor handphone tidak ditemukan!"
                        validatePassword()
                    }

                }

            })

        }
    }


    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_login_at_login -> {
                if (isInternetConnected(this)) {
                    validateNoHp()
                } else {
                    alertDialogConnection()
                }
            }

            R.id.btn_register_at_login -> {
                goToRegister()
            }

            R.id.btn_lupa_password -> {
                goToForgotPassword()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

}