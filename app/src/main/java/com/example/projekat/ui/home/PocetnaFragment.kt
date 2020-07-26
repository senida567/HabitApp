package com.example.projekat.ui.home

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.projekat.AppDatabase
import com.example.projekat.R
import com.example.projekat.activity.MainActivity.Companion.db
import com.example.projekat.adapter.AktivnostiAdapter
import com.example.projekat.adapter.InkrementalneAdapter
import com.example.projekat.adapter.PocetneAktivnostiAdapter
import com.example.projekat.entity.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.kategorije_fragment.*
import kotlinx.android.synthetic.main.pocetna_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class PocetnaFragment(db : AppDatabase, listaAktivnosti: List<Aktivnosti>, listaTipovaAktivnosti: List<String>) : Fragment() {

    private var db : AppDatabase
    // iz ove liste aktivnosti mi treba samo nekoliko elemeneta koje cu proslijediti adapteru
    private var listaAktivnosti : List<Aktivnosti>
    private var listaTipovaAktivnosti : List<String>
    private lateinit var inkrementalneList : List<Inkrementalne>
    private lateinit var kolicinskeList : List<Kolicinske>
    private lateinit var vremenskeList : List<Vremenske>

    //lateinit private var inkrementalneAdapter : InkrementalneAdapter
    // todo: lateinit private var vremenskeAdapter : VremenskeAdapter
    // todo: lateinit private var kolicinskeAdapter : KolicinskeAdapter

    lateinit var fabPocetna : FloatingActionButton

    lateinit var dialog: AlertDialog
    lateinit var odabraneAktivnosti : ArrayList<Int>  // u ovu listu smjestam aktivnosti iz alert dialoga koje korisnik odabere za dodavanje na pocetnu stranicu

    init {
        this.db = db
        this.listaAktivnosti = listaAktivnosti
        this.listaTipovaAktivnosti = listaTipovaAktivnosti
    }

  //  private lateinit var viewModel: PocetnaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?  = inflater.inflate(R.layout.pocetna_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // RecyclerView node initialized here

        dodajListe()

        recyclerViewPocetna.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            // listaAktivnosti.slice(0..3) - uzima prvi tri elementa iz liste
            adapter = PocetneAktivnostiAdapter(listaAktivnosti, listaTipovaAktivnosti)
        }

        fabPocetna = view.findViewById(R.id.fabPocetna)

        fabPocetna.setOnClickListener {
            dodajAktivnost(view)
        }
    }

    private fun dodajListe() {
        Log.d(ContentValues.TAG, "INICIJALIZACIJA LISTI")
        CoroutineScope(Dispatchers.Default).launch {
            inkrementalneList = db.inkrementalneDao().getAll()
            vremenskeList = db.vremenskeDao().getAll()
            kolicinskeList = db.kolicinskeDao().getAll()
        }
    }

    fun povecajInkrement(aktivnost: Inkrementalne) {
        // u bazi se nalazi inkrement koji je korisnik odabrao prilikom kreiranja aktivnosti
        // za taj inkrement treba povecati broj
        aktivnost.broj += aktivnost.inkrement
    }

    fun smanjiInkrement(aktivnost: Inkrementalne) {
        aktivnost.broj -= aktivnost.inkrement
    }

    fun dodajUnos(aktivnost : Kolicinske) {

    }

    private fun dodajAktivnost(view : View) {
        /* todo: korisniku se prikaze lista vec postojecih aktivnosti u bazi
        *   aktivnost koju odabere se dodaje na pocetnu stranicu */

        odabraneAktivnosti = ArrayList<Int>()

        val builder = context?.let { AlertDialog.Builder(it) }
        if (builder != null) {
            builder.setTitle("Odaberite aktivnost")
        }

        // dodajemo checkbox listu
        // trebam dodati listAdapter pomocu kojeg cu u dialogu prikazati aktivnosti iz baze
        val aktivnosti = arrayOf("akt1", "akt2", "akt3", "akt4", "akt5")
        val checkedItems = null
        if (builder != null) {
            builder.setMultiChoiceItems(aktivnosti, checkedItems) { dialog, which, isChecked ->
                // ako je data aktivnost bila checkirana pa je korisnik uncheckirao box
                // onda uklanjam tu aktivnost iz liste
                if (odabraneAktivnosti.contains(which)) {
                    odabraneAktivnosti.remove(Integer.valueOf(which))
                }
                else {
                    // ako je korisnik checkirao box, odgovarajucu aktivnost dodajem u listu odabraneAktivnosti
                    odabraneAktivnosti.add(which)
                }
            }
        }

        // dodajem OK i cancel dugmad
        if (builder != null) {
            builder.setPositiveButton("OK") { dialog, which ->
                // user clicked OK
            }
        }

        if (builder != null) {
            builder.setNegativeButton("Cancel", null)
        }

        // create and show the alert dialog
        val dialog = builder?.create()
        if (dialog != null) {
            dialog.show()
        }
    }

    fun closeDialog(view: View) {
        dialog.dismiss()
        Toast.makeText(context, "Dialog Closed", Toast.LENGTH_SHORT).show()
    }

    private fun ukloniAktivnost(view : View) {
        /* todo: odgovarajuca aktivnost se uklanja s pocetne strane
        *   ne uklanja se iz baze */
    }
}