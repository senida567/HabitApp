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
import com.example.projekat.entity.Inkrementalne
import com.example.projekat.entity.Kategorije
import com.example.projekat.entity.Kolicinske
import com.example.projekat.entity.Vremenske

class AktivnostiAdapter(kategorije : Kategorije, inkrementalneAktivnostiLista: List<Inkrementalne>, kolicinskeAktivnostiLista : List<Kolicinske>,
                        vremenskeAktivnostiLista : List<Vremenske>, mOnElementListener: OnElementListener)
    : RecyclerView.Adapter<AktivnostiAdapter.AktivnostiViewHolder>() {

    private var inkremnetalneAktivnostiLista: List<Inkrementalne>
    private var kolicinskeAktivnostiLista : List<Kolicinske>
    private var vremenskeAktivnostiLista : List<Vremenske>
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

        if(position < inkremnetalneAktivnostiLista.size) {
            //holder.naziv.setText(inkremnetalneAktivnostiLista.get(position).naziv)
            holder.editBroj.setText(inkremnetalneAktivnostiLista.get(position).broj)
            holder.editInkrement.setText(inkremnetalneAktivnostiLista.get(position).inkrement)
            holder.mjernaJedinica.setVisibility(View.GONE)
            holder.editMjernaJedinica.setVisibility(View.GONE)
            holder.editKolicina.setVisibility(View.GONE)
            holder.kolicina.setVisibility(View.GONE)
            holder.pocetak.setVisibility(View.GONE)
            holder.editPocetak.setVisibility(View.GONE)
            holder.kraj.setVisibility(View.GONE)
            holder.editKraj.setVisibility(View.GONE)
        }else if(position < inkremnetalneAktivnostiLista.size + kolicinskeAktivnostiLista.size) {
           // holder.naziv.setText(kolicinskeAktivnostiLista.get(position).naziv)
            holder.editBroj.setVisibility(View.GONE)
            holder.broj.setVisibility(View.GONE)
            holder.editInkrement.setVisibility(View.GONE)
            holder.inkrement.setVisibility(View.GONE)
            //holder.editMjernaJedinica.setText(kolicinskeAktivnostiLista.get(position).id_mjerna_jedinica)
            holder.editKolicina.setText(kolicinskeAktivnostiLista.get(position).kolicina)
            holder.pocetak.setVisibility(View.GONE)
            holder.editPocetak.setVisibility(View.GONE)
            holder.kraj.setVisibility(View.GONE)
            holder.editKraj.setVisibility(View.GONE)
        } else {
           // holder.naziv.setText(vremenskeAktivnostiLista.get(position).naziv)
            holder.editBroj.setVisibility(View.GONE)
            holder.broj.setVisibility(View.GONE)
            holder.editInkrement.setVisibility(View.GONE)
            holder.inkrement.setVisibility(View.GONE)
            holder.editMjernaJedinica.setVisibility(View.GONE)
            holder.mjernaJedinica.setVisibility(View.GONE)
            holder.editKolicina.setVisibility(View.GONE)
            holder.kolicina.setVisibility(View.GONE)
            holder.editPocetak.setText(vremenskeAktivnostiLista.get(position).pocetak)
            holder.editKraj.setText(vremenskeAktivnostiLista.get(position).kraj)
        }

    }

    override fun getItemCount(): Int {
        return inkremnetalneAktivnostiLista.size + kolicinskeAktivnostiLista.size + vremenskeAktivnostiLista.size
    }

    class AktivnostiViewHolder(itemView: View, onElementListener: OnElementListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var naziv : TextView
        var editBroj : TextView
        var broj : TextView
        var editInkrement : TextView
        var inkrement : TextView
        var editKolicina : TextView
        var kolicina : TextView
        var editPocetak : TextView
        var pocetak : TextView
        var editKraj : TextView
        var kraj : TextView
        var editMjernaJedinica : TextView
        var mjernaJedinica : TextView
        var mOnElementListener: OnElementListener

        override fun onClick(view: View) {
            Log.d(ContentValues.TAG, "onClick: $adapterPosition")
            mOnElementListener.onElementClick(adapterPosition)
        }

        init {
            naziv = itemView.findViewById(R.id.naziv)
            editBroj = itemView.findViewById(R.id.editBroj)
            broj = itemView.findViewById(R.id.broj)
            editInkrement = itemView.findViewById(R.id.editInkrement)
            inkrement = itemView.findViewById(R.id.inkrement)
            editKolicina = itemView.findViewById(R.id.editKolicina)
            kolicina = itemView.findViewById(R.id.kolicina)
            editPocetak = itemView.findViewById(R.id.editPocetak)
            pocetak = itemView.findViewById(R.id.pocetak)
            editKraj = itemView.findViewById(R.id.editKraj)
            kraj = itemView.findViewById(R.id.kraj)
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
        this.inkremnetalneAktivnostiLista = inkrementalneAktivnostiLista
        this.vremenskeAktivnostiLista = vremenskeAktivnostiLista
        this.kolicinskeAktivnostiLista = kolicinskeAktivnostiLista
        this.mOnElementListener = mOnElementListener
    }


}

