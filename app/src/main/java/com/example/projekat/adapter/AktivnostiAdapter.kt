package com.example.projekat.adapter

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projekat.R
import com.example.projekat.entity.*

class AktivnostiAdapter(kategorije : Kategorije, listaAktivnosti : List<Aktivnosti>, mOnElementListener: OnElementListener)
    : RecyclerView.Adapter<AktivnostiAdapter.AktivnostiViewHolder>() {

    private var listaAktivnosti : List<Aktivnosti>
    private var kategorije : Kategorije
    private var mOnElementListener : OnElementListener

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AktivnostiViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.aktivnosti_element, parent, false)
        return AktivnostiViewHolder(view, mOnElementListener)
    }

    override fun onBindViewHolder(holder: AktivnostiViewHolder, position: Int) {
        //val kategorije : Kategorije = kategorijeLista.get(position)
        //holder.naziv.setText(kategorije.naziv)
        //holder.osobina.setText(kategorije.osobina.toString()) //zasad je osobina boolean
        //i sve što korisnik treba vidjeti iz tebele kategorije


    }

    override fun getItemCount(): Int {
        return listaAktivnosti.size
    }

    class AktivnostiViewHolder(itemView: View, onElementListener: OnElementListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var editMjernaJedinica : TextView
        var mjernaJedinica : TextView
        var mOnElementListener: OnElementListener

        override fun onClick(view: View) {
            Log.d(ContentValues.TAG, "onClick: $adapterPosition")
            mOnElementListener.onElementClick(adapterPosition)
        }

        init {

            editMjernaJedinica = itemView.findViewById(R.id.editMJ)
            mjernaJedinica = itemView.findViewById(R.id.mjernaJedinica)

            mOnElementListener = onElementListener
            itemView.setOnClickListener(this)
        }
    }

    interface OnElementListener{
        fun onElementClick(position : Int);
    }

    init {
        this.listaAktivnosti = listaAktivnosti
        this.kategorije = kategorije
        this.mOnElementListener = mOnElementListener
    }


}

