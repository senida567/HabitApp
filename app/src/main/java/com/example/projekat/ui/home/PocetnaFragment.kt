package com.example.projekat.ui.home

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.projekat.AppDatabase
import com.example.projekat.R
import com.example.projekat.activity.MainActivity.Companion.db
import com.example.projekat.entity.Inkrementalne
import com.example.projekat.entity.Kolicinske
import com.example.projekat.entity.Vremenske
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PocetnaFragment : Fragment(), View.OnClickListener {

    lateinit private var inkrementalneList : List<Inkrementalne>
    lateinit private var kolicinskeList : List<Kolicinske>
    lateinit private var vremenskeList : List<Vremenske>

    lateinit private var recyclerView : RecyclerView
    //lateinit private var inkrementalneAdapter : InkrementalneAdapter
    // todo: lateinit private var vremenskeAdapter : VremenskeAdapter
    // todo: lateinit private var kolicinskeAdapter : KolicinskeAdapter

    lateinit var fabPocetna : FloatingActionButton
    lateinit var dugmeUnos : Button
    lateinit var dugmePlus : Button
    lateinit var dugmeMinus : Button
    lateinit var dugmeUkloni : Button
/*
    var akt = arrayOf(
        "akt1", "akt2", "akt3",
        "akt4", "akt5", "akt6", "akt7"
    )

    lateinit var adapter: ArrayAdapter<String>
    lateinit var listView: ListView
    lateinit var alertDialog: AlertDialog.Builder*/
    lateinit var dialog: AlertDialog

    companion object {
        fun newInstance() = PocetnaFragment()
    }

    private lateinit var viewModel: PocetnaViewModel

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

        //setUpMyRecyclerView()
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
  /*      alertDialog = context?.let { AlertDialog.Builder(it) }!!
        val rowList: View = layoutInflater.inflate(R.layout.dodaj_aktivnost_element, null)
        listView = rowList.findViewById(R.id.aktivnost_element)
        adapter = context?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, akt) }!!
        listView.adapter = adapter
        adapter.notifyDataSetChanged()
        alertDialog.setView(rowList)
        dialog = alertDialog.create()
        dialog.show()*/
        val builder = context?.let { AlertDialog.Builder(it) }
        if (builder != null) {
            builder.setTitle("Odaberite aktivnost")
        }

        // dodajemo checkbox listu
        val aktivnosti = arrayOf("akt1", "akt2", "akt3", "akt4", "akt5")
        val checkedItems = booleanArrayOf(true, false, false, true, false)
        if (builder != null) {
            builder.setMultiChoiceItems(aktivnosti, checkedItems) { dialog, which, isChecked ->
                // user checked or unchecked a box
            }
        }

        // add OK and Cancel buttons
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

    private suspend fun setUpMyRecyclerView() {

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

        inkrementalneList = db!!.inkrementalneDao()?.getAll() as List<Inkrementalne>
        kolicinskeList = db!!.kolicinskeDao()?.getAll() as List<Kolicinske>
        vremenskeList = db!!.vremenskeDao()?.getAll() as List<Vremenske>

       // recyclerView = getView()?.findViewById(R.id.recyclerViewPocetna)!!

        //inkrementalneAdapter = InkrementalneAdapter(inkrementalneList)
        // todo: uraditi i za vremenske i kolicinske aktivnosti

        //recyclerView.adapter = inkrementalneAdapter
        //recyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun onClick(v: View?) {
        print("nestooo")
    }

}