package com.example.projekat.ui.categories

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projekat.AppDatabase
import com.example.projekat.R
import com.example.projekat.adapter.AktivnostiAdapter
import com.example.projekat.entity.Aktivnosti
import com.example.projekat.entity.Kategorije
import kotlinx.android.synthetic.main.kategorije_fragment.*

class AktivnostiFragment(kategorije: Kategorije, db : AppDatabase, izbrisi : Boolean) : Fragment(), AktivnostiAdapter.OnElementListener {

    private var listaAktivnosti: List<Aktivnosti>
    private var kategorije: Kategorije
    private var db : AppDatabase
    private var tipAktivnosti : Int

    private lateinit var btnIzbrisi: Button
    private lateinit var btnDodaj :Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_aktivnosti, container, false)

    // populate the views now that the layout has been inflated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // RecyclerView node initialized here
        postaviNazive()

        recyclerView.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            adapter = AktivnostiAdapter(
                tipAktivnosti, listaAktivnosti, db, this@AktivnostiFragment
            )
        }

        btnIzbrisi = view.findViewById(R.id.izbrisiKategoriju)
        btnIzbrisi.setOnClickListener {
             izbrisiKategoriju()
        }
        btnDodaj = view.findViewById(R.id.dodajAktivnost)
        btnDodaj.setOnClickListener {
            dodajAktivnost()
        }

    }

    fun izbrisiKategoriju() {
        val fr = getFragmentManager()?.beginTransaction()
        Log.d(TAG, "izbrisiKategoriju: " + kategorije.id)
        val alertDialog: FragmentDialog = FragmentDialog(db, kategorije.id)
        alertDialog.show(fr!!, "fragment_alert")
    }

    init {
        this.kategorije = kategorije
        this.db = db
        this.listaAktivnosti = db.aktivnostiDao().getById_K(kategorije.id)
        this.tipAktivnosti = kategorije.tip
    }

    fun postaviNazive() {

        val textView1: TextView? = view?.findViewById(R.id.nazivKategorije)
        if (textView1 != null) {
            textView1.setText(kategorije.naziv)
        }
        var textView2: TextView? = view?.findViewById(R.id.osobineKategorije)
        if (textView2 != null && kategorije.osobina != "") {
            textView2.text = kategorije.osobina
        }
        var textView3 : TextView? = view?.findViewById(R.id.tipKategorije)
        if(textView3 != null) {
            textView3.text = db.tipoviAktivnostiDao().getNazivById(kategorije.tip)
        }

    }

    override fun onElementClick(position: Int) {
        Log.d(TAG, "onElementClick: " + position)
        val fr = getFragmentManager()?.beginTransaction()
        Log.d(TAG, "otvoriAktivnost: " + kategorije.id)
        val alertDialog: FragmentDialog_ZaAktivnost = FragmentDialog_ZaAktivnost(db, listaAktivnosti.get(position))
        alertDialog.show(fr!!, "fragment_alert")
    }


    fun dodajAktivnost() {
        val fr = getFragmentManager()?.beginTransaction()
        Log.d(TAG, "dodajAktivnost: " + kategorije.id)
        val alertDialog: DodajNovuAktivnostFragment = DodajNovuAktivnostFragment(db, kategorije.id)
        alertDialog.show(fr!!, "fragment_alert")
    }

}

