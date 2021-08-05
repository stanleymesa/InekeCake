package com.example.inekecake

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class FragmentNama : Fragment(R.layout.fragment_nama){

    private lateinit var mainActivity: MainActivity
    private lateinit var listener: FragmentNamaListener
    private lateinit var etNama: EditText

    interface FragmentNamaListener{
        fun onNamaWrited(nama: String)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        listener = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etNama = view.findViewById(R.id.et_nama)

        listener.onNamaWrited(etNama.text.toString())

//            Toast.makeText(mainActivity, "Memilih : ${radioButton.text}", Toast.LENGTH_SHORT).show()s

    }



}
