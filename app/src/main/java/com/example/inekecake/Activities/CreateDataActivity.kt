package com.example.inekecake.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.inekecake.API.APIRequestData
import com.example.inekecake.API.RetroServer
import com.example.inekecake.Model.ResponseModel
import com.example.inekecake.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateDataActivity : AppCompatActivity(), View.OnClickListener {

private lateinit var etNama: EditText
private lateinit var etNoHp: EditText
private lateinit var etAlamat: EditText
private lateinit var etCake: EditText
private lateinit var etHarga: EditText
private lateinit var etTglPesan: EditText
private lateinit var etTglKirim: EditText
private lateinit var btnSave: Button
private lateinit var nama: String
private lateinit var noHp: String
private lateinit var alamat: String
private lateinit var cake: String
private lateinit var harga: String
private lateinit var tglPesan: String
private lateinit var tglKirim: String

    override fun onCreate(savedInstanceState: Bundle?) {
        // set no dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_data)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Tambah Data Customer"

        // deklarasi
        etNama = findViewById(R.id.et_nama)
        etNoHp = findViewById(R.id.et_noHp)
        etAlamat = findViewById(R.id.et_alamat)
        etCake = findViewById(R.id.et_cake)
        etHarga = findViewById(R.id.et_harga)
        etTglPesan = findViewById(R.id.et_tglPesan)
        etTglKirim = findViewById(R.id.et_tglKirim)
        btnSave = findViewById(R.id.btn_save)

        btnSave.setOnClickListener(this)
    }

    private fun saveData() {
        val ardData: APIRequestData = RetroServer.konekRetrofit().create(APIRequestData::class.java)
        val simpanData: Call<ResponseModel> = ardData.ardPostData(nama, noHp, alamat, cake, harga, tglPesan, tglKirim)

        simpanData.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                val message = response.body()?.message
                Toast.makeText(this@CreateDataActivity, "Saved!, $message", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                Toast.makeText(this@CreateDataActivity, "${t.message}", Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_save -> {
                nama = etNama.text.toString().trim()
                noHp = etNoHp.text.toString().trim()
                alamat = etAlamat.text.toString().trim()
                cake = etCake.text.toString().trim()
                harga = etHarga.text.toString().trim()
                tglPesan = etTglPesan.text.toString().trim()
                tglKirim = etTglKirim.text.toString().trim()

                var kosong = false

                if (nama.isEmpty()) {
                    etNama.error = "Nama tidak boleh kosong"
                    kosong = true
                }
                if (noHp.isEmpty()) {
                    etNoHp.error = "No.Handphone tidak boleh kosong"
                    kosong = true
                }
                if (alamat.isEmpty()) {
                    etAlamat.error = "Alamat tidak boleh kosong"
                    kosong = true
                }
                if (cake.isEmpty()) {
                    etCake.error = "Cake tidak boleh kosong"
                    kosong = true
                }
                if (harga.isEmpty()) {
                    etHarga.error = "Harga tidak boleh kosong"
                    kosong = true
                }
                if (tglPesan.isEmpty()) {
                    etTglPesan.error = "Tanggal Pesan tidak boleh kosong"
                    kosong = true
                }
                if (tglKirim.isEmpty()) {
                    etTglKirim.error = "Tanggal Kirim tidak boleh kosong"
                    kosong = true
                }
                if (!kosong) {
                    saveData()
                    finish()
                }
            }
        }
    }
}