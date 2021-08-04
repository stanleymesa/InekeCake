package com.example.inekecake

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity(), FragmentColours.FragmentColoursListener {

    private lateinit var radioGroup1: RadioGroup
    private lateinit var radioGroup2: RadioGroup
    private lateinit var radioButton: RadioButton
    private lateinit var cakeImage: ImageView
    private lateinit var fragmentColours: FragmentColours

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        radioGroup1 = findViewById(R.id.radioGroup1)
//        radioGroup2 = findViewById(R.id.radioGroup2)
        cakeImage = findViewById(R.id.cakeImage)
        fragmentColours = FragmentColours()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentColours, fragmentColours)
            .commit()

    }

    override fun onItemClicked(colour: String) {
        when(colour) {
            "Blue Ocean" -> {
                cakeImage.setImageResource(R.drawable.bluecakecoba)
            }
            "Plain" -> {
                cakeImage.setImageResource(R.drawable.plaincake)
            }
        }
    }

//    fun buttonClick(view: View) {
//        // cek apakah radiogroup1 atau radiogroup2 bertabrakan
//
//        var checkedButton1: Int = radioGroup1.checkedRadioButtonId
//
//        radioGroup2.clearCheck()
//        radioButton = findViewById(checkedButton1)
//
//        when(radioButton.text) {
//            "Blue" -> {
//                cakeImage.setImageResource(R.drawable.bluecakecoba)
//            }
//            "Plain" -> {
//                cakeImage.setImageResource(R.drawable.plaincake)
//            }
//        }
//
//        Toast.makeText(this, "Memilih : ${radioButton.text}", Toast.LENGTH_SHORT ).show()
//    }
//
//    fun buttonClick2(view:View) {
//        radioGroup1.clearCheck()
//
//    }




}