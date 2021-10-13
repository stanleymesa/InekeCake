package com.example.inekecake.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import com.chaos.view.PinView
import com.example.inekecake.model.UserModel
import com.example.inekecake.R
import com.example.inekecake.session.SessionManager
import com.google.android.gms.tasks.OnSuccessListener
import java.util.concurrent.TimeUnit
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class VerifyOtpActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var tvNoHp: TextView
    private lateinit var pinView: PinView
    private lateinit var btnVerify: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var codeBySystem: String
    private lateinit var pbVerify: ProgressBar
    private lateinit var firestoreRoot: FirebaseFirestore
    private lateinit var dataUser: UserModel
    private lateinit var noHp: String
    private lateinit var fromWhere: String
    private lateinit var userSession: SessionManager
    private lateinit var rememberMeSession: SessionManager
    private var isRememberMe = false
    var clickedBack = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        // set no dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_otp)
        supportActionBar?.hide()

        tvNoHp = findViewById(R.id.tv_noHp_verify)
        pinView = findViewById(R.id.pinView_otp)
        btnVerify = findViewById(R.id.btn_verify)
        pbVerify = findViewById(R.id.pb_verify)
        btnVerify.setOnClickListener(this)

        // Set Firebase
        auth = FirebaseAuth.getInstance()
        firestoreRoot = Firebase.firestore

        // Set Session
        userSession = SessionManager(this, SessionManager.LOGIN_SESSION)
        rememberMeSession = SessionManager(this, SessionManager.REMEMBERME_SESSION)

        // Get Intent
        fromWhere = intent.getStringExtra("from").toString()
        // Jika berasal dari register
        if (fromWhere.equals("register")) {
            dataUser = intent.getParcelableExtra("dataUser")!!
            noHp = dataUser.noHp
        }
        // Jika berasal dari Forgot password
        else if (fromWhere.equals("forgotPassword")) {
            noHp = intent.getStringExtra("noHp").toString()
        }
        // Jika berasal dari First Auth
        else if (fromWhere.equals("firstAuth")) {
            noHp = intent.getStringExtra("noHp").toString()
        }
        // Jika berasal dari login account changed
        else {
            dataUser = intent.getParcelableExtra("dataUser")!!
            noHp = dataUser.noHp
            isRememberMe = intent.getBooleanExtra("isRememberMe", false)
        }


        tvNoHp.setText(noHp)

        sendOtpCodeToUser(noHp)

    }

    // STEP 1 , INISIASI
    private fun sendOtpCodeToUser(noHp: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(noHp)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)         // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    // STEP 2, MENGIRIMKAN KODE OTP KE HP
    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
            super.onCodeSent(p0, p1)
            codeBySystem = p0
            pbVerify.isVisible = false
        }

        override fun onVerificationCompleted(p0: PhoneAuthCredential) {
            val kodeSms = p0.smsCode
            if (kodeSms != null) {
                pinView.setText(kodeSms)
                verifyCode(kodeSms)
            }
        }

        override fun onVerificationFailed(p0: FirebaseException) {
            pbVerify.isVisible = false
            Toast.makeText(this@VerifyOtpActivity, "${p0.message}", Toast.LENGTH_LONG).show()
            val intent = Intent(this@VerifyOtpActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    // STEP 3, VERIFIKASI KODE
    private fun verifyCode(p0: String) {
        val credential = PhoneAuthProvider.getCredential(codeBySystem, p0)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    // Save Data User (from Register)
                    if (fromWhere.equals("register")) {
                        firestoreRoot.document("users/${auth.currentUser!!.uid}").set(dataUser)
                            .addOnSuccessListener(this, object : OnSuccessListener<Void> {
                                override fun onSuccess(p0: Void) {
                                    Toast.makeText(this@VerifyOtpActivity, "Verification Complete!", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this@VerifyOtpActivity, DashboardActivity::class.java)
                                    startActivity(intent)

                                    // Create User Session
                                    userSession.createLogin(dataUser)
                                    finish()
                                }
                            })
                    }
                    // End Save Data User

                    // To New Password (from Forgot Password)
                     else if (fromWhere.equals("forgotPassword")) {
                        val intent = Intent(this, NewPasswordActivity::class.java)
                        intent.putExtra("noHp", noHp)
                        startActivity(intent)
                        finish()
                    }
                    // End To New Password

                    // To Login (First Auth)
                    else if (fromWhere.equals("firstAuth")) {
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    // End To Login

                    // To Dashboard (Login Account Changed)
                    else {
                        val intent = Intent(this, DashboardActivity::class.java)
                        startActivity(intent)
                        userSession.createLogin(dataUser)

                        if (isRememberMe) {
                            rememberMeSession.createRememberMe()
                        }
                        finish()
                    }
                    // End To Dashboard



                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        pbVerify.isVisible = false
                        Toast.makeText(this, "Verification Failed!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_verify -> {
                val kodeManual = pinView.text.toString()
                if (!kodeManual.isEmpty()) {
                    verifyCode(kodeManual)
                }
            }
        }
    }

    override fun onBackPressed() {
        clickedBack++
        if (clickedBack < 2) {
            Toast.makeText(this, "Tekan sekali lagi untuk keluar aplikasi", Toast.LENGTH_SHORT).show()
        } else {
            finishAffinity()
        }
    }
}