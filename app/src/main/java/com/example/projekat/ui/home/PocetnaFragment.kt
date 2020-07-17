package com.example.projekat.ui.home

import android.content.ContentValues
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.projekat.AppDatabase
import com.example.projekat.R
import com.example.projekat.activity.MainActivity.Companion.db
import com.example.projekat.adapter.AktivnostiAdapter
import com.example.projekat.adapter.InkrementalneAdapter
import com.example.projekat.entity.Inkrementalne
import com.example.projekat.entity.Kategorije
import com.example.projekat.entity.Kolicinske
import com.example.projekat.entity.Vremenske
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.kategorije_fragment.*
import kotlinx.coroutines.launch

class PocetnaFragment : Fragment() {

    lateinit private var inkrementalneList : List<Inkrementalne>
    lateinit private var kolicinskeList : List<Kolicinske>
    lateinit private var vremenskeList : List<Vremenske>
    private lateinit var kategorije: Kategorije

    lateinit private var recyclerView : RecyclerView
    //lateinit private var inkrementalneAdapter : InkrementalneAdapter
    // todo: lateinit private var vremenskeAdapter : VremenskeAdapter
    // todo: lateinit private var kolicinskeAdapter : KolicinskeAdapter

    lateinit var fabPocetna : FloatingActionButton
    lateinit var dugmeUnos : Button
    lateinit var dugmePlus : Button
    lateinit var dugmeMinus : Button
    lateinit var dugmeUkloni : Button

    lateinit var dialog: AlertDialog
    lateinit var odabraneAktivnosti : ArrayList<Int>  // u ovu listu smjestam aktivnosti iz alert dialoga koje korisnik odabere za dodavanje na pocetnu stranicu

    companion object {
        fun newInstance() = PocetnaFragment()
    }

    private lateinit var viewModel: PocetnaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.pocetna_fragment, container, false)

        fabPocetna = view.findViewById(R.id.fabPocetna)
/*        dugmePlus = view.findViewById(R.id.plus_btn)
        dugmeMinus = view.findViewById(R.id.minus_btn)
        dugmeUnos = view.findViewById(R.id.unos_btn)
        dugmeUkloni = view.findViewById(R.id.ukloni_aktivnost)

        dugmePlus.setOnClickListener {
            povecajInkrement(view)
        }

        dugmeMinus.setOnClickListener {
            smanjiInkrement(view)
        }

        dugmeUnos.setOnClickListener {
            dodajUnos(view)
        }

        dugmeUkloni.setOnClickListener {
            ukloniAktivnost(view)
        }*/

        fabPocetna.setOnClickListener {
            dodajAktivnost(view)
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PocetnaViewModel::class.java)
        // TODO: Use the ViewModel

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // RecyclerView node initialized here

        // setUpMyRecyclerView je suspend funkcija pa mora biti pozvana iz korutine
        // zato koristim sljedeci coroutine builder
        lifecycleScope.launch {
            setUpMyRecyclerView()
        }
/*
        recyclerView.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            adapter = AktivnostiAdapter(
                kategorije, inkrementalneList, kolicinskeList,
                vremenskeList, this@PocetnaFragment
            )
        }
*/
    }

    private fun povecajInkrement(view: View) {

    }

    private fun smanjiInkrement(view: View) {

    }

    private fun dodajUnos(view : View) {

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

    open suspend fun setUpMyRecyclerView() {

        /*if (db?.inkrementalneDao()?.getAll()!!.isEmpty() ||
            db?.kolicinskeDao()?.getAll()!!.isEmpty() ||
            db?.vremenskeDao()?.getAll()!!.isEmpty()) {

            val aktivnost_1 : Inkrementalne =
                Inkrementalne(0,"Voda", 0, 1, 1, 0)
            appDatabase!!.getInkrementalneService()?.saveOrUpdate(aktivnost_1)

            val aktivnost_2 : Kolicinske =
                Kolicinske(0, "Kalorije", 1400, 2, 1)
            appDatabase!!.getKolicinskeService()?.saveOrUpdate(aktivnost_2)

            val aktivnost_3 : Vremenske =
                Vremenske(0, "Trčanje", "start", "stop", 3)
            appDatabase!!.getVremenskeService()?.saveOrUpdate(aktivnost_3)
        }*/

        inkrementalneList = db?.inkrementalneDao()?.getAll()!!
        vremenskeList = db?.vremenskeDao()?.getAll()!!
        kolicinskeList = db?.kolicinskeDao()?.getAll()!!

        recyclerView = getView()?.findViewById(R.id.recyclerViewPocetna)!!
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = InkrementalneAdapter(inkrementalneList)
        //inkrementalneAdapter = InkrementalneAdapter(inkrementalneList)
        // todo: uraditi i za vremenske i kolicinske aktivnosti

    }
/*
    override fun onElementClick(position: Int) {
        TODO("Not yet implemented")
        Log.d(ContentValues.TAG, "onElementClick: " + position)
    }
*/
}