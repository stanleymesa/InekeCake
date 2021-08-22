package com.example.inekecake.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.*
import com.example.inekecake.Activities.MainActivity
import com.example.inekecake.R

class FragmentColours : Fragment(R.layout.fragment_colours){

    private lateinit var radioGroup: RadioGroup
    private lateinit var radioButton: RadioButton
    private lateinit var mainActivity: MainActivity
    private lateinit var listener: FragmentColoursListener

    interface FragmentColoursListener{
        fun onColourClicked(colour: String)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        listener = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        radioGroup = view.findViewById(R.id.radioGroup1)

        radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            radioButton = view.findViewById(checkedId)
            listener.onColourClicked(radioButton.text.toString())
//            Toast.makeText(mainActivity, "Memilih : ${radioButton.text}", Toast.LENGTH_SHORT).show()
        })

    }



}
