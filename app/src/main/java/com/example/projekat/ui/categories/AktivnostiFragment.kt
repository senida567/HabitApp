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
import androidx.annotation.RestrictTo
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.example.projekat.AppDatabase
import com.example.projekat.R
import com.example.projekat.activity.MainActivity.Companion.db
import com.example.projekat.adapter.AktivnostiAdapter
import com.example.projekat.entity.*
import kotlinx.android.synthetic.main.kategorije_fragment.recyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Appendable
import kotlin.coroutines.coroutineContext

class AktivnostiFragment(kategorije: Kategorije, db : AppDatabase) : Fragment(), AktivnostiAdapter.OnElementListener {

    private lateinit var listaAktivnosti: List<Aktivnosti>
    private var kategorije: Kategorije
    private var db : AppDatabase

    private lateinit var btnIzbrisi: Button

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
        popuniListe()
        postaviNazive()

        recyclerView.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            adapter = AktivnostiAdapter(
                kategorije, listaAktivnosti, this@AktivnostiFragment
            )
        }

        btnIzbrisi = view.findViewById(R.id.izbrisiKategoriju)
        btnIzbrisi.setOnClickListener {
            // izbrisiKategoriju()
        }

    }

    init {
        this.kategorije = kategorije
        this.db = db
    }

    fun popuniListe() {
        CoroutineScope(Dispatchers.Default).launch {
            listaAktivnosti = db.aktivnostiDao().getById_K(kategorije.id)
            Log.d(TAG, "popuniListe: ")
        }
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

