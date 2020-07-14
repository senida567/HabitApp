package com.example.projekat.ui.categories

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.projekat.R
import com.example.projekat.activity.MainActivity.Companion.db
import com.example.projekat.adapter.AktivnostiAdapter
import com.example.projekat.entity.*
import kotlinx.android.synthetic.main.kategorije_fragment.recyclerView
import kotlinx.coroutines.launch

class AktivnostiFragment(kategorije: Kategorije) : Fragment(), AktivnostiAdapter.OnElementListener {

    private lateinit var inkrementalneAktivnostiList: List<Inkrementalne>
    private lateinit var vremenskeAktivnostiList: List<Vremenske>
    private lateinit var kolicinskeAktivnostiList: List<Kolicinske>
    private lateinit var listaAktivnosti: List<List<String>>
    private var kategorije: Kategorije

    private lateinit var btnIzbrisi: Button
    private lateinit var btnDodajI: Button
    private lateinit var btnDodajK: Button
    private lateinit var btnDodajV: Button
    private var brojKlikova = 0

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

        lifecycleScope.launch {
            popuniListe()
        }
        postaviNazive()

        recyclerView.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            adapter = AktivnostiAdapter(
                kategorije, inkrementalneAktivnostiList, kolicinskeAktivnostiList,
                vremenskeAktivnostiList, this@AktivnostiFragment
            )
        }

        btnIzbrisi = view.findViewById(R.id.izbrisiKategoriju)
        btnIzbrisi.setOnClickListener {
            // izbrisiKategoriju()
        }

        btnDodajI = view.findViewById(R.id.dodajAktivnostInkrementalna)
        btnDodajI.setOnClickListener {
            dodajAktivnost("inkrementalna")
        }
        btnDodajK = view.findViewById(R.id.dodajAktivnostKolicinska)
        btnDodajK.setOnClickListener {
            dodajAktivnost("kolicinska")
        }
        btnDodajV = view.findViewById(R.id.dodajAktivnostVremenska)
        btnDodajV.setOnClickListener {
            dodajAktivnost("vremenska")
        }

    }

    companion object {
        fun newInstance(): KategorijeFragment = KategorijeFragment()
    }

    init {
        this.kategorije = kategorije
    }

    open suspend fun popuniListe() {
        inkrementalneAktivnostiList = db?.inkrementalneDao()?.getAll()!!
        vremenskeAktivnostiList = db?.vremenskeDao()?.getAll()!!
        kolicinskeAktivnostiList = db?.kolicinskeDao()?.getAll()!!

        /* var brojAktivnosti = inkrementalneAktivnostiList.size + vremenskeAktivnostiList.size + kolicinskeAktivnostiList.size
        var i = 0
        var j = 0
        while (brojAktivnosti > 0) {
            while(j < inkrementalneAktivnostiList.size) {
                var lista : List<String> = listOf("inkrementalne",inkrementalneAktivnostiList.get(i).naziv,
                    inkrementalneAktivnostiList.get(i).)
                listaAktivnosti.toMutableList().add(i, lista)
                j++
            }
            brojAktivnosti--
            i++
        }*/
    }

    open fun postaviNazive() {

        var textView1: TextView? = view?.findViewById(R.id.nazivKategorije)
        if (textView1 != null) {
            textView1.setText(kategorije.naziv)
        }

        var textView2: TextView? = view?.findViewById(R.id.osobineKategorije)
        /* if (textView2 != null && kategorije.osobina == true) {
            val osob : List<Osobine>? = GlavnaAktivnost.appDatabase?.getOsobineDao()?.getAll()
            var tekst = ""
            if(osob != null) {
                for(o : Osobine in osob) {
                    if(o.id_kategorije == kategorije.id) {
                        tekst += " ! "
                        tekst += o.opis
                    }
                }
            }
            textView2.setText(tekst)*/
    }

    override fun onElementClick(position: Int) {
        Log.d(TAG, "onElementClick: " + position)

        /*   var fr = getFragmentManager()?.beginTransaction()
           if(position < inkrementalneAktivnostiList.size)
               fr?.replace(R.id.fragment_container, UrediAktivnostFragment("inkrementalne",
                   inkrementalneAktivnostiList.get(position).id))
           else if(position < inkrementalneAktivnostiList.size + kolicinskeAktivnostiList.size)
               fr?.replace(R.id.fragment_container, UrediAktivnostFragment("kolicinske",
                   kolicinskeAktivnostiList.get(position).id))
           else
               fr?.replace(R.id.fragment_container, UrediAktivnostFragment("vremenske",
                   vremenskeAktivnostiList.get(position).id))
           fr?.addToBackStack(null)
           fr?.commit()*/

    }


    fun dodajAktivnost(s: String) {

        /* var fr = getFragmentManager()?.beginTransaction()
        fr?.replace(R.id.fragment_container, DodajAktivnostFragment(kategorije, s))
        fr?.addToBackStack(null)
        fr?.commit()
    }

    open fun izbrisiKategoriju() {
        brojKlikova++
        if(brojKlikova == 1)
            Toast.makeText(activity, "Kliknite ponovo za brisanje!", Toast.LENGTH_LONG).show()
        else if(brojKlikova == 2) {
            brojKlikova = 0
            GlavnaAktivnost.appDatabase?.getOsobineDao()?.deleteKategoriju(kategorije.id)
            GlavnaAktivnost.appDatabase?.getVremenskeDao()?.deleteKategoriju(kategorije.id)
            GlavnaAktivnost.appDatabase?.getInkrementalneDao()?.deleteKategoriju(kategorije.id)
            GlavnaAktivnost.appDatabase?.getKolicinskeDao()?.deleteKategoriju(kategorije.id)
            GlavnaAktivnost.appDatabase?.getKategorijeDao()?.deleteId(kategorije.id)

            var fr = getFragmentManager()?.beginTransaction()
            fr?.replace(R.id.fragment_container, KategorijeFragment())
            fr?.addToBackStack(null)
            fr?.commit()
        }*/
    }
}

