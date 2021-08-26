package com.example.inekecake.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.inekecake.API.APIRequestData
import com.example.inekecake.API.RetroServer
import com.example.inekecake.Model.DataModel
import com.example.inekecake.Model.ResponseModel
import com.example.inekecake.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateDataActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var etNama: EditText
    private lateinit var etNoHp: EditText
    private lateinit var etAlamat: EditText
    private lateinit var etCake: EditText
    private lateinit var etHarga: EditText
    private lateinit var etTglPesan: EditText
    private lateinit var etTglKirim: EditText
    private lateinit var btnSave: Button
    private var id: Int? = 0
    private lateinit var nama: String
    private lateinit var noHp: String
    private lateinit var alamat: String
    private lateinit var cake: String
    private lateinit var harga: String
    private lateinit var tglPesan: String
    private lateinit var tglKirim: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_data)
        supportActionBar?.title = "Edit Data Customer"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // deklarasi
        etNama = findViewById(R.id.et_edit_nama)
        etNoHp = findViewById(R.id.et_edit_noHp)
        etAlamat = findViewById(R.id.et_edit_alamat)
        etCake = findViewById(R.id.et_edit_cake)
        etHarga = findViewById(R.id.et_edit_harga)
        etTglPesan = findViewById(R.id.et_edit_tglPesan)
        etTglKirim = findViewById(R.id.et_edit_tglKirim)
        btnSave = findViewById(R.id.btn_edit_save)

        btnSave.setOnClickListener(this)

        // Ambil data dari Customer Activity Intent
        val dataCustomer: DataModel? = intent.getParcelableExtra("dataEdit")

        // Set Old Value to EditText
        id = dataCustomer?.id
        etNama.setText(dataCustomer?.nama)
        etNama.setSelection(etNama.text.length)
        etNoHp.setText(dataCustomer?.noHp)
        etAlamat.setText(dataCustomer?.alamat)
        etCake.setText(dataCustomer?.cake)
        etHarga.setText(dataCustomer?.harga)
        etTglPesan.setText(dataCustomer?.tglPesan)
        etTglKirim.setText(dataCustomer?.tglKirim)
    }

    private fun updateData() {
        val ardUpdateData: APIRequestData = RetroServer.konekRetrofit().create(APIRequestData::class.java)
        val ubahData: Call<ResponseModel> = ardUpdateData.ardUpdateData(id ?: 0, nama, noHp, alamat, cake, harga, tglPesan, tglKirim)

        ubahData.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                val message = response.body()?.message
                Toast.makeText(this@UpdateDataActivity, "Saved!, $message", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                Toast.makeText(this@UpdateDataActivity, "${t.message}", Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_edit_save -> {
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
                    updateData()
                    finish()
                }
            }
        }
    }
}