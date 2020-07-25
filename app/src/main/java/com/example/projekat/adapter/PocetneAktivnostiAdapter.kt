package com.example.projekat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projekat.R
import com.example.projekat.entity.Aktivnosti
import com.example.projekat.entity.Inkrementalne
import com.example.projekat.entity.Kolicinske
import com.example.projekat.entity.Vremenske
import com.example.projekat.ui.home.PocetnaFragment

class PocetneAktivnostiAdapter(listaAktivnosti: List<Aktivnosti>, listaTipova: List<String>, mOnElementListener: AktivnostiAdapter.OnElementListener)
    : RecyclerView.Adapter<PocetneAktivnostiAdapter.BaseViewHolder>() {

    private var listaAktivnosti : List<Aktivnosti>
    // iz main-a prosljedjujem i listu tipova aktivnosti tako da se element u listaAktivnosti
    // i njegov tip u listaTipovaAktivnosti nalaze na istom indeksu u listama
    private var listaTipovaAktivnosti : List<String>
    private var mOnElementListener : AktivnostiAdapter.OnElementListener

    init {
        this.listaAktivnosti = listaAktivnosti
        this.mOnElementListener = mOnElementListener
        this.listaTipovaAktivnosti = listaTipova
    }

    companion object {
        private const val TYPE_INKREMENTALNE = 0
        private const val TYPE_VREMENSKE = 1
        private const val TYPE_KOLICINSKE = 2
    }

    /* When the Adapter is initialized, it will populate PocetneAktivnostiHoldere in the RecyclerView
     with the onCreateViewHolder(…) method. */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder {
        return when (viewType) {
            TYPE_INKREMENTALNE -> {
                val view: View =
                    LayoutInflater.from(parent.context).inflate(R.layout.pocetna_inkrementalna_element, parent, false)
                InkrementalneViewHolder(view)
            }
            TYPE_VREMENSKE -> {
                val view: View =
                    LayoutInflater.from(parent.context).inflate(R.layout.pocetna_vremenska_element, parent, false)
                VremenskeViewHolder(view)
            }
            TYPE_KOLICINSKE -> {
                val view: View =
                    LayoutInflater.from(parent.context).inflate(R.layout.pocetna_kolicinska_element, parent, false)
                KolicinskeViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
        /*val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.pocetna_fragment, parent, false)
        return PocetneAktivnostiViewHolder(view, mOnElementListener)*/
    }

    override fun getItemCount(): Int {
        return listaAktivnosti.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val element = listaAktivnosti[position]
        when (holder) {
            // prosljedjujem element (aktivnost) viewHolderu
            is InkrementalneViewHolder -> holder.bind(element)
            is VremenskeViewHolder -> holder.bind(element)
            is KolicinskeViewHolder -> holder.bind(element)
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemViewType(position: Int): Int {
        val aktivnost = listaTipovaAktivnosti.get(position)

        return when (aktivnost) {
            "Inkrementalne" -> TYPE_INKREMENTALNE
            "Vremenske" -> TYPE_VREMENSKE
            "Kolicinske" -> TYPE_KOLICINSKE
            else -> throw IllegalArgumentException("Invalid type of data " + position)
        }
    }

    abstract class BaseViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        abstract fun bind (aktivnost : Aktivnosti)
    }

    class InkrementalneViewHolder(itemView: View) : BaseViewHolder(itemView) {

        var nazivAktivnosti : TextView
        var unos : TextView
        var mjernaJedinica : TextView
        var plusDugme : Button
        var minusDugme : Button

        init {
            nazivAktivnosti = itemView.findViewById(R.id.naziv_aktivnosti_pocetna) // ovo ima svaki tip aktivnosti
            unos = itemView.findViewById(R.id.unos_pocetna) // ovo ima svaki tip aktivnosti
            mjernaJedinica = itemView.findViewById(R.id.mjerna_jedinica_pocetna) // ovo nemaju vremenske aktivnosti
            plusDugme = itemView.findViewById(R.id.plus_btn) // imaju samo inkrementalne aktivnosti
            minusDugme = itemView.findViewById(R.id.minus_btn) // imaju samo inkrementalne aktivnosti
        }

        override fun bind(aktivnost: Aktivnosti) {
            TODO("Not yet implemented")
        }
    }

    class KolicinskeViewHolder(itemView: View) : BaseViewHolder(itemView) {

        var nazivAktivnosti : TextView
        var unos : TextView
        var mjernaJedinica : TextView
        var dodajUnos : Button

        init {
            nazivAktivnosti = itemView.findViewById(R.id.naziv_aktivnosti_pocetna) // ovo ima svaki tip aktivnosti
            unos = itemView.findViewById(R.id.unos_pocetna) // ovo ima svaki tip aktivnosti
            mjernaJedinica = itemView.findViewById(R.id.mjerna_jedinica_pocetna) // ovo nemaju vremenske aktivnosti
            dodajUnos = itemView.findViewById(R.id.unos_btn) // ovo imaju samo kolicinske aktivnosti
        }

        override fun bind(aktivnost: Aktivnosti) {
            TODO("Not yet implemented")
        }
    }

    class VremenskeViewHolder(itemView: View) : BaseViewHolder(itemView) {

        var nazivAktivnosti : TextView
        var unos : TextView
        var startStop : Button

        init {
            nazivAktivnosti = itemView.findViewById(R.id.naziv_aktivnosti_pocetna) // ovo ima svaki tip aktivnosti
            unos = itemView.findViewById(R.id.unos_pocetna) // ovo ima svaki tip aktivnosti
            startStop = itemView.findViewById(R.id.start_stop_btn) // imaju samo vremenske aktivnosti
        }
        override fun bind(aktivnost: Aktivnosti) {
            TODO("Not yet implemented")
        }
    }

    /*
    /* The method onBindViewHolder(…) will take a data collection and apply a rotating rendering
    of visible data applied to those ViewHolders. */
    override fun onBindViewHolder(holder: PocetneAktivnostiViewHolder, position: Int) {
        val aktivnost : Aktivnosti = listaAktivnosti.get(0)
        /* Trebam napraviti if uslove:
          ako je aktivnost tipa inkrementalna onda inicijalizirati varijable za nju
          ako je aktivnost tipa kolicinska onda inicijalizirati varijable za nju
          ako je aktivnost tipa vremenska onda inicijalizirati varijable za nju */
        holder.nazivAktivnosti.text = aktivnost.naziv
        // ovdje inicijalizirati ostale varijable koje imaju svi tipovi aktivnosti
        // holder.unos.text = // ne mogu aktivnosti u bazi imati samo one kolone, treba jos dodati kolonu za sve varijable tj sve sto se pojavi u tom redu

        // provjera tipa aktivnosti
        //var tipAktivnosti : String
        // todo: kako preko Aktivnosti dobiti tip aktivnosti
        CoroutineScope(Dispatchers.Default).launch {
            holder.tipAktivnosti = db?.tipoviAktivnostiDao()?.getById(aktivnost.id)?.naziv.toString() // mozda ce praviti problem safe call-ovi
        }

        // ako je tip aktivnosti inkrementalne:

        // ako je tip aktivnosti vremenske:

        // ako je tip aktivnosti kolicinske:

    }

    class PocetneAktivnostiViewHolder(itemView: View, onElementListener: AktivnostiAdapter.OnElementListener):
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var nazivAktivnosti : TextView
        lateinit var tipAktivnosti : String
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

    }*/
}