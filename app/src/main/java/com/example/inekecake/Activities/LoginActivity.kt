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
import com.example.inekecake.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity(),
    View.OnClickListener{

    private lateinit var logoAtLogin: ImageView
    private lateinit var sloganAtLogin: TextView
    private lateinit var usernameAtLogin: TextInputLayout
    private lateinit var passwordAtLogin: TextInputLayout
    private lateinit var btnRegister: Button
    private lateinit var btnLogin: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            val fade = Fade()
//            val decor = window.decorView
//            val actionBar: View = decor.findViewById(R.id.action_bar_container)
//            fade.excludeTarget(actionBar, true)
//            fade.excludeTarget(android.R.id.statusBarBackground, true)
//            fade.excludeTarget(android.R.id.navigationBarBackground, true)
//            window.enterTransition = fade
//            window.exitTransition = fade
//        }

        supportActionBar?.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        logoAtLogin = findViewById(R.id.logo_inekecake_login)
        sloganAtLogin = findViewById(R.id.slogan_inekecake_login)
        usernameAtLogin = findViewById(R.id.et_username_login)
        passwordAtLogin = findViewById(R.id.et_password_login)
        btnLogin = findViewById(R.id.btn_login_at_login)
        btnRegister = findViewById(R.id.btn_register_at_login)

        btnLogin.setOnClickListener(this)
        btnRegister.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_login_at_login -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

            R.id.btn_register_at_login -> {
                var pairs = ArrayList<android.util.Pair<View, String>>()
                pairs.add(android.util.Pair(logoAtLogin, "logo"))
                pairs.add(android.util.Pair(sloganAtLogin, "slogan"))
                pairs.add(android.util.Pair(usernameAtLogin, "username"))
                pairs.add(android.util.Pair(passwordAtLogin, "password"))
                pairs.add(android.util.Pair(btnLogin, "login"))
                pairs.add(android.util.Pair(btnRegister, "register"))

                val intent = Intent(this, RegisterActivity::class.java)

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
        super.onBackPressed()
        finishAffinity()
    }

}