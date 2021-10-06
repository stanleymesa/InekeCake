package com.example.inekecake.Activities

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.media.Image
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.transition.Fade
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import com.example.inekecake.R
import com.example.inekecake.Session.SessionManager

@Suppress("DEPRECATION")
class SplashActivity() : AppCompatActivity() {

    private lateinit var logoAnim: Animation
    private lateinit var sloganAnim: Animation
    private lateinit var imgLogo: ImageView
    private lateinit var slogan: TextView
    private lateinit var rememberMeSession: SessionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        // set no dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // set no dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        // end set
        supportActionBar?.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        imgLogo = findViewById(R.id.logo_inekecake)
        slogan = findViewById(R.id.slogan_inekecake)

        logoAnim = AnimationUtils.loadAnimation(this, R.anim.logo_from_right)
        sloganAnim = AnimationUtils.loadAnimation(this, R.anim.logo_from_left)

        // set animasi

        imgLogo.animation = logoAnim
        slogan.animation = sloganAnim

        // SET SESSION
        rememberMeSession = SessionManager(this, SessionManager.REMEMBERME_SESSION)


        Handler().postDelayed(object : Runnable {
            override fun run() {

                val intentToLogin = Intent(this@SplashActivity, LoginActivity::class.java)
                val intentToDashboard = Intent(this@SplashActivity, DashboardActivity::class.java)

                // JIKA ADA REMEMBER ME SESSION
                if (rememberMeSession.isRememberedMe()) {
                    startActivity(intentToDashboard)
                } else {
                    val pairs = ArrayList<android.util.Pair<View, String>>()
                    pairs.add(android.util.Pair(imgLogo, "logo"))
                    pairs.add(android.util.Pair(slogan, "slogan"))
                    val options = ActivityOptions.makeSceneTransitionAnimation(this@SplashActivity,pairs[0], pairs[1])
                    startActivity(intentToLogin, options.toBundle())

                }
            }
        }, 3000)


    }



}