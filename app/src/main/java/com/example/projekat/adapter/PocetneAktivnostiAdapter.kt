package com.example.projekat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projekat.R
import com.example.projekat.entity.Aktivnosti

class PocetneAktivnostiAdapter(listaAktivnosti : List<Aktivnosti>, mOnElementListener: AktivnostiAdapter.OnElementListener): RecyclerView.Adapter<PocetneAktivnostiAdapter.PocetneAktivnostiViewHolder>() {
    /* List<Aktivnosti> sam prebacila u List<Any> jer ova lista sadrzi elemente koji mogu biti
    * ili List<Inkrementalne> ili List<Kolicinske> ili List>Vremenske> */
    private var listaAktivnosti : List<Aktivnosti>
    private var mOnElementListener : AktivnostiAdapter.OnElementListener

    init {
        this.listaAktivnosti = listaAktivnosti
        this.mOnElementListener = mOnElementListener
    }

    /* When the Adapter is initialized, it will populate PocetneAktivnostiHoldere in the RecyclerView
     with the onCreateViewHolder(…) method. */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PocetneAktivnostiViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.aktivnosti_element, parent, false)
        return PocetneAktivnostiViewHolder(view, mOnElementListener)
    }

    override fun getItemCount(): Int {
        return listaAktivnosti.size
    }

    /* The method onBindViewHolder(…) will take a data collection and apply a rotating rendering
    of visible data applied to those ViewHolders. */
    override fun onBindViewHolder(holder: PocetneAktivnostiViewHolder, position: Int) {
        val aktivnost : Aktivnosti = listaAktivnosti.get(0)
        /* Trebam napraviti if uslove:
          ako je aktivnost tipa inkrementalna onda inicijalizirati varijable za nju
          ako je aktivnost tipa kolicinska onda inicijalizirati varijable za nju
          ako je aktivnost tipa vremenska onda inicijalizirati varijable za nju */
        holder.nazivAktivnosti.text = aktivnost.naziv

    }

    class PocetneAktivnostiViewHolder(itemView: View, onElementListener: AktivnostiAdapter.OnElementListener):
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var nazivAktivnosti : TextView
        var unos : TextView
        var mjernaJedinica : TextView
        var ukloniAktivnost : Button
        var startStop : Button
        var dodajUnos : Button
        var plusDugme : Button
        var minusDugme : Button
        var mOnElementListener: AktivnostiAdapter.OnElementListener

        init {

            nazivAktivnosti = itemView.findViewById(R.id.naziv_aktivnosti_pocetna) // ovo ima svaki tip aktivnosti
            unos = itemView.findViewById(R.id.unos_pocetna) // ovo ima svaki tip aktivnosti
            mjernaJedinica = itemView.findViewById(R.id.mjerna_jedinica_pocetna) // ovo nemaju vremenske aktivnosti
            ukloniAktivnost = itemView.findViewById(R.id.ukloni_aktivnost) // ovo ima svaki tip aktivnosti
            startStop = itemView.findViewById(R.id.start_stop_btn) // imaju samo vremenske aktivnosti
            dodajUnos = itemView.findViewById(R.id.unos_btn) // ovo imaju samo kolicinske aktivnosti
            plusDugme = itemView.findViewById(R.id.plus_btn) // imaju samo inkrementalne aktivnosti
            minusDugme = itemView.findViewById(R.id.minus_btn) // imaju samo inkrementalne aktivnosti
            mOnElementListener = onElementListener
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            mOnElementListener.onElementClick(adapterPosition)
        }

    }
}