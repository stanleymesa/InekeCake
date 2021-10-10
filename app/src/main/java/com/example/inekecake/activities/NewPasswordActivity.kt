
package com.example.inekecake.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.inekecake.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NewPasswordActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var etNewPassword: TextInputLayout
    private lateinit var etConfirmNewPassword: TextInputLayout
    private lateinit var btnSaveNewPassword: Button
    private lateinit var newPassword: String
    private lateinit var confirmNewPassword: String
    private lateinit var firestoreRoot: FirebaseFirestore
    private lateinit var noHp: String

    override fun onCreate(savedInstanceState: Bundle?) {
        // set no dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_password)

        supportActionBar?.hide()


        etNewPassword = findViewById(R.id.et_newpassword)
        etConfirmNewPassword = findViewById(R.id.et_confirm_newpassword)
        btnSaveNewPassword = findViewById(R.id.btn_save_newpasword)

        // SET FIREBASE
        firestoreRoot = Firebase.firestore

        noHp = intent.getStringExtra("noHp").toString()

        btnSaveNewPassword.setOnClickListener(this)

    }

    private fun validateNewPassword(): Boolean {
        newPassword = etNewPassword.editText?.text.toString()
        val valid = Regex("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}\$")

        etConfirmNewPassword.error = null

        if (newPassword.isEmpty()) {
            etNewPassword.error = "Password tidak boleh kosong"
            return false
        }else if (!valid.matches(newPassword)) {
            etNewPassword.error = "Password lemah!\n(minimal 8 karakter, gunakan 1 huruf besar dan angka)"
            return false
        } else {
            etNewPassword.error = null
            return true
        }
    }

    private fun validateConfirmNewPassword(): Boolean {
        confirmNewPassword = etConfirmNewPassword.editText?.text.toString()

        if (confirmNewPassword.isEmpty()) {
            etConfirmNewPassword.error = "Confirm Password tidak boleh kosong"
            return false
        } else {
            etConfirmNewPassword.error = null
            return true
        }
    }

    private fun allValidation() {
        if (validateNewPassword()) {
            if (validateConfirmNewPassword()) {
                if (newPassword.equals(confirmNewPassword)) {
                    firestoreRoot.document("users/$noHp").update("password", newPassword)
                    Toast.makeText(this@NewPasswordActivity, "Password berhasil diperbaharui!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@NewPasswordActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    etConfirmNewPassword.error = "Konfirmasi password anda berbeda"
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_save_newpasword -> {
                allValidation()
            }
        }
    }
}