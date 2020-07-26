package com.example.projekat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projekat.R
import com.example.projekat.entity.Inkrementalne

class InkrementalneAdapter(inkrementLista: List<Inkrementalne>) :
        RecyclerView.Adapter<InkrementalneAdapter.InkrementalneViewHolder>() {

    private var inkrementalneLista : List<Inkrementalne>

    init {
        this.inkrementalneLista = inkrementLista
    }

    class InkrementalneViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var nazivAktivnosti : TextView
        var unos : TextView
        var mjernaJedinica : TextView

        init {
            nazivAktivnosti = itemView.findViewById(R.id.naziv_aktivnosti_pocetna)
            unos = itemView.findViewById(R.id.unos_pocetna)
            mjernaJedinica = itemView.findViewById(R.id.mjerna_jedinica_pocetna_inkrementalna)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InkrementalneViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.pocetna_inkrementalna_element, parent, false)
        return InkrementalneViewHolder(view)
    }

    override fun getItemCount(): Int {
        return inkrementalneLista.size
    }

    override fun onBindViewHolder(holder: InkrementalneViewHolder, position: Int) {

        val inkrementalne : Inkrementalne = inkrementalneLista.get(position)
       // holder.nazivAktivnosti.setText(inkrementalne.naziv)
        holder.unos.setText(inkrementalne.broj.toString())
        // todo: treba naziv a ne id mjerne jedinice
       // holder.mjernaJedinica.setText(inkrementalne.id_mjerna_jedinica)
    }

}