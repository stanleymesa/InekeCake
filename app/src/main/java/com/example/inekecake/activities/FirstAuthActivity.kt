package com.example.inekecake.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.inekecake.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class FirstAuthActivity: AppCompatActivity(), View.OnClickListener {

    private lateinit var btnNoHpAuth: MaterialButton
    private lateinit var tiNoHpAuth: TextInputLayout
    private lateinit var noHp: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_auth)

        // Hooks
        btnNoHpAuth = findViewById(R.id.btn_noHp_auth)
        tiNoHpAuth = findViewById(R.id.ti_noHp_auth)

        btnNoHpAuth.setOnClickListener(this)
    }

    private fun validateNoHp() {
        val noHpAwal = tiNoHpAuth.editText?.text.toString().trim()
        if (noHpAwal.isEmpty()) {
            tiNoHpAuth.error = "Nomor handphone tidak boleh kosong"
        } else {
            if (noHpAwal.get(0).toString().equals("0")) {
                noHp = "+62" + noHpAwal.drop(1)
            } else {
                noHp = "+62" + noHpAwal
            }

            // INTENT KE VERIFY
            val intent = Intent(this, VerifyOtpActivity::class.java)
            intent.putExtra("noHp", noHp)
            intent.putExtra("from", "firstAuth")
            startActivity(intent)
            finish()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_noHp_auth -> {
                validateNoHp()
            }
        }
    }
}