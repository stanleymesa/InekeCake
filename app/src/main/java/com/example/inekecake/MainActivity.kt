package com.example.inekecake

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.core.view.marginLeft
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity(),
    FragmentColours.FragmentColoursListener,
    FragmentTopping.FragmentToppingListener,
    FragmentNama.FragmentNamaListener,
    View.OnClickListener {

    private lateinit var cakeImage: ImageView
    private lateinit var buttonNext: Button
    private lateinit var buttonBack: Button
    private lateinit var buttonFinish: Button
    private lateinit var fragmentColours: FragmentColours
    private lateinit var fragmentTopping: FragmentTopping
    private lateinit var fragmentNama: FragmentNama
    private var currentPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        declaration()

        buttonNext.isVisible = true
        buttonBack.isVisible = false
        buttonFinish.isVisible = false
        buttonNext.setOnClickListener(this)
        buttonBack.setOnClickListener(this)


        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragmentColours)
            .commit()

    }

    // deklarasi
    fun declaration(){
        cakeImage = findViewById(R.id.cakeImage)
        buttonNext = findViewById(R.id.buttonNext)
        buttonBack = findViewById(R.id.buttonBack)
        buttonFinish = findViewById(R.id.buttonFinish)
        fragmentColours = FragmentColours()
        fragmentTopping = FragmentTopping()
        fragmentNama = FragmentNama()
    }

    // Colour
    override fun onColourClicked(colour: String) {
        when(colour) {
            "Blue Ocean" -> {
                cakeImage.setImageResource(R.drawable.bluecakecoba)
            }
            "Plain" -> {
                cakeImage.setImageResource(R.drawable.plaincakefix)
            }
        }
    }

    // Topping
    override fun onToppingClicked(topping: String) {
    }

    // Tambah Nama
    override fun onNamaWrited(nama: String) {
    }

    // Button Next
    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.buttonNext -> {
                currentPage++
                when (currentPage) {
                    2 -> {
                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left)
                            .replace(R.id.fragmentContainer, fragmentTopping)
                            .commit()
                        buttonNext.isVisible = true
                        buttonBack.isVisible = true
                        buttonFinish.isVisible = false
                    }

                    3 -> {
                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left)
                            .replace(R.id.fragmentContainer, fragmentNama)
                            .commit()
                        buttonNext.setText("finish")
                        buttonNext.isVisible = false
                        buttonBack.isVisible = true
                        buttonFinish.isVisible = true
                    }
                }
            }

            R.id.buttonBack -> {
                currentPage--
                when (currentPage) {
                    1 -> {
                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right)
                            .replace(R.id.fragmentContainer, fragmentColours)
                            .commit()
                        buttonNext.isVisible = true
                        buttonBack.isVisible = false
                        buttonFinish.isVisible = false
                    }

                    2 -> {
                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right)
                            .replace(R.id.fragmentContainer, fragmentTopping)
                            .commit()
                        buttonNext.setText("next")
                        buttonNext.isVisible = true
                        buttonBack.isVisible = true
                        buttonFinish.isVisible = false
                    }
                }
            }
        }


    }




}