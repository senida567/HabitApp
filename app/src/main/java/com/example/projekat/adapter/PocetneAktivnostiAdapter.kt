package com.example.projekat.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projekat.AppDatabase
import com.example.projekat.DAO.InkrementalneDao
import com.example.projekat.R
import com.example.projekat.activity.MainActivity.Companion.db
import com.example.projekat.entity.Aktivnosti
import com.example.projekat.entity.Inkrementalne
import com.example.projekat.entity.Kolicinske
import com.example.projekat.entity.Vremenske
import com.example.projekat.ui.home.PocetnaFragment

class PocetneAktivnostiAdapter(listaAktivnosti: List<Aktivnosti>, listaTipova: List<String>)
    : RecyclerView.Adapter<PocetneAktivnostiAdapter.BaseViewHolder>() {

    private var listaAktivnosti : List<Aktivnosti>
    // iz main-a prosljedjujem i listu tipova aktivnosti tako da se element u listaAktivnosti
    // i njegov tip u listaTipovaAktivnosti nalaze na istom indeksu u listama
    private var listaTipovaAktivnosti : List<String>

    init {
        this.listaAktivnosti = listaAktivnosti
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

    /* The method onBindViewHolder(…) will take a data collection and apply a rotating rendering
    of visible data applied to those ViewHolders. */
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
            nazivAktivnosti = itemView.findViewById(R.id.naziv_aktivnosti_pocetna_inkrementalna) // ovo ima svaki tip aktivnosti
            unos = itemView.findViewById(R.id.unos_pocetna_inkrementalna) // ovo ima svaki tip aktivnosti
            mjernaJedinica = itemView.findViewById(R.id.mjerna_jedinica_pocetna_inkrementalna) // ovo nemaju vremenske aktivnosti
            plusDugme = itemView.findViewById(R.id.plus_btn) // imaju samo inkrementalne aktivnosti
            minusDugme = itemView.findViewById(R.id.minus_btn) // imaju samo inkrementalne aktivnosti
        }

        override fun bind(aktivnost: Aktivnosti) {
            // broj i inkrement mozda trebaju unutar setonclicklistenera
            val broj = db?.inkrementalneDao()?.getBrojByIdAktivnosti(aktivnost.id)
            val inkrement = db?.inkrementalneDao()?.getInkrementByIdAktivnosti(aktivnost.id)

            nazivAktivnosti.text = aktivnost.naziv
            // todo: mjernu jedinicu mi ne prikazuje
            mjernaJedinica.text =
                db?.mjerneJediniceDao()?.getByIdAktivnosti(aktivnost.id) // mjernu jedinicu mozemo dobiti po id-u aktivnosti
             unos.text = broj.toString()

            plusDugme.setOnClickListener {
                // u bazi se nalazi inkrement koji je korisnik odabrao prilikom kreiranja aktivnosti
                // za taj inkrement treba povecati broj
                if (broj != null) {
                    db?.inkrementalneDao()?.updateBroj(broj + inkrement!!, aktivnost.id)
                }
            }

            minusDugme.setOnClickListener {
                // u bazi se nalazi inkrement koji je korisnik odabrao prilikom kreiranja aktivnosti
                // za taj inkrement treba umanjiti broj
                if (broj != null) {
                    db?.inkrementalneDao()?.updateBroj(broj - inkrement!!, aktivnost.id)
                }
            }
        }
    }

    class KolicinskeViewHolder(itemView: View) : BaseViewHolder(itemView) {

        var nazivAktivnosti : TextView
        var unos : TextView
        var mjernaJedinica : TextView
        var dodajUnos : Button

        init {
            nazivAktivnosti = itemView.findViewById(R.id.naziv_aktivnosti_pocetna_kolicinska) // ovo ima svaki tip aktivnosti
            unos = itemView.findViewById(R.id.unos_pocetna_kolicinska) // ovo ima svaki tip aktivnosti
            mjernaJedinica = itemView.findViewById(R.id.mjerna_jedinica_pocetna_kolicinska) // ovo nemaju vremenske aktivnosti
            dodajUnos = itemView.findViewById(R.id.unos_btn) // ovo imaju samo kolicinske aktivnosti
        }

        override fun bind(aktivnost: Aktivnosti) {
            nazivAktivnosti.text = aktivnost.naziv
            mjernaJedinica.text =
                db?.mjerneJediniceDao()?.getByIdAktivnosti(aktivnost.id)
            unos.text = db?.kolicinskeDao()?.getKolicinaById(aktivnost.id).toString()
        }
    }

    class VremenskeViewHolder(itemView: View) : BaseViewHolder(itemView) {

        var nazivAktivnosti : TextView
        var unos : TextView
        var startStop : Button

        init {
            nazivAktivnosti = itemView.findViewById(R.id.naziv_aktivnosti_pocetna_vremenska) // ovo ima svaki tip aktivnosti
            unos = itemView.findViewById(R.id.unos_pocetna_vremenska) // ovo ima svaki tip aktivnosti
            startStop = itemView.findViewById(R.id.start_stop_btn) // imaju samo vremenske aktivnosti
        }
        override fun bind(aktivnost: Aktivnosti) {
            nazivAktivnosti.text = aktivnost.naziv
        }
    }
/*
    fun postaviListener(listener: MojClickListener) {
        this.listener = listener
    }

    interface MojClickListener {
        fun povecajInkrement(aktivnost : Inkrementalne)
        fun smanjiInkrement(aktivnost : Inkrementalne)
        fun dodajUnos(aktivnost : Kolicinske)
    }*/
/*
    class PocetneAktivnostiViewHolder(itemView: View, onElementListener: AktivnostiAdapter.OnElementListener):
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var mOnElementListener: AktivnostiAdapter.OnElementListener

        init {
            mOnElementListener = onElementListener
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            mOnElementListener.onElementClick(adapterPosition)
        }

    }*/
}