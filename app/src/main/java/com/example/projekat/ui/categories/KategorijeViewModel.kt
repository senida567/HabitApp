package com.example.projekat.ui.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projekat.R

class KategorijeViewModel(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.kategorije_element, parent, false)) {
    private var naziv: EditText? = null
    private var osobina: EditText? = null
    private var tip : EditText? = null

    init {
        naziv = itemView.findViewById(R.id.naziv)
        osobina = itemView.findViewById(R.id.osobina)
        tip = itemView.findViewById(R.id.tip)
    }

}