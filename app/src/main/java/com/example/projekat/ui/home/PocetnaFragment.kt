package com.example.projekat.ui.home

import android.content.ContentValues
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projekat.AppDatabase
import com.example.projekat.R
import com.example.projekat.activity.MainActivity
import com.example.projekat.adapter.AktivnostiAdapter
import com.example.projekat.adapter.PocetneAktivnostiAdapter
import com.example.projekat.entity.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.pocetna_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PocetnaFragment(db : AppDatabase, listaAktivnosti: List<Aktivnosti>) : Fragment() {

    private var db: AppDatabase

    // iz ove liste aktivnosti koja sadrzi sve aktivnosti mi treba samo nekoliko elemenata koje cu proslijediti adapteru
    private var listaAktivnosti: List<Aktivnosti>

    // lista s par pocetnih aktivnosti koju prosljedjujem adapteru
    lateinit private var glavneAktivnosti: ArrayList<GlavneAktivnosti>
    private var listaTipovaAktivnosti = arrayListOf<Int>()

    private lateinit var inkrementalneList: List<Inkrementalne>
    private lateinit var kolicinskeList: List<Kolicinske>
    private lateinit var vremenskeList: List<Vremenske>

    //lateinit private var inkrementalneAdapter : InkrementalneAdapter
    // todo: lateinit private var vremenskeAdapter : VremenskeAdapter
    // todo: lateinit private var kolicinskeAdapter : KolicinskeAdapter

    lateinit var fabPocetna: FloatingActionButton

    lateinit var odabraneAktivnosti: ArrayList<Int>  // u ovu listu smjestam aktivnosti iz alert dialoga koje korisnik odabere za dodavanje na pocetnu stranicu

    init {
        this.db = db
        this.listaAktivnosti = listaAktivnosti
    }

    //  private lateinit var viewModel: PocetnaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.pocetna_fragment, container, false)

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
            adapter = PocetneAktivnostiAdapter(db, glavneAktivnosti, listaTipovaAktivnosti).apply {
                    povecajInkrement = { aktivnost ->
                        // u bazi se nalazi inkrement koji je korisnik odabrao prilikom kreiranja aktivnosti
                        // za taj inkrement treba povecati broj

                        db.glavneAktivnostiDao().updateBroj(aktivnost.broj + aktivnost.inkrement, aktivnost.id) // postavlja u bazu
                        aktivnost.broj = aktivnost.broj + aktivnost.inkrement
                        adapter?.notifyDataSetChanged()
                        Log.d("BROJ POVECAN, SADA je", aktivnost.broj.toString())
                    }
                smanjiInkrement = { aktivnost ->
                    if (aktivnost.broj > 0) {
                        db.glavneAktivnostiDao().updateBroj(aktivnost.broj - aktivnost.inkrement, aktivnost.id) // postavlja u bazu
                        aktivnost.broj = aktivnost.broj - aktivnost.inkrement
                        adapter?.notifyDataSetChanged()
                        Log.d("BROJ UMANJEN, SADA je", aktivnost.broj.toString())
                    }
                }
                unesiKolicinu = { aktivnost ->
                    val builder = context?.let { AlertDialog.Builder(it) }
                    val inputField = EditText(context)
                    inputField.maxLines = 1

                    val layout = FrameLayout(context)

                    layout.setPadding(45,15,45,0) //postavljam padding
                    layout.addView(inputField)
                    inputField.setRawInputType(InputType.TYPE_CLASS_NUMBER)

                    if (builder != null) {
                        builder.setView(layout)
                    }

                    if (builder != null) {
                        builder.setTitle("Unesite količinu")
                    }

                    // dodajem OK i cancel dugmad
                    if (builder != null) {
                        builder.setPositiveButton("OK") { dialog, which ->
                            val kolicina = inputField.text.toString().toInt()

                            aktivnost.unos = kolicina
                            db.glavneAktivnostiDao().updateUnos(kolicina, aktivnost.id)

                            adapter?.notifyDataSetChanged()
                        }
                    }

                    if (builder != null) {
                        builder.setNegativeButton("Cancel", null)
                    }

                    // kreiram i prikazujem alert dialog
                    val dialog = builder?.create()
                    if (dialog != null) {
                        dialog.show()
                    }

                }
                zapocniStopericu = { aktivnost ->
                    Toast.makeText(requireActivity(), "Stoperica pokrenuta!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        fabPocetna = view.findViewById(R.id.fabPocetna)

        fabPocetna.setOnClickListener {
            dodajAktivnost(view)
        }
    }

    private fun dodajListe() {
        glavneAktivnosti = ArrayList(db.glavneAktivnostiDao().getAll())

        for (el in glavneAktivnosti) {
            listaTipovaAktivnosti.add(db.kategorijeDao().getTipById(el.id_kategorije))
        }

        Log.d(ContentValues.TAG, "INICIJALIZACIJA LISTI")
        Log.d("GLAVNE AKTIVNOSTI LISTA", glavneAktivnosti.size.toString())
    }

    fun povecajInkrement2(aktivnost: GlavneAktivnosti) {
        // u bazi se nalazi inkrement koji je korisnik odabrao prilikom kreiranja aktivnosti
        // za taj inkrement treba povecati broj
        val broj = aktivnost.broj
        val inkrement = aktivnost.inkrement

        MainActivity.db?.glavneAktivnostiDao()?.updateBroj(broj + inkrement, aktivnost.id)
        //db?.inkrementalneDao()?.updateBroj(broj + inkrement, aktivnost.id)
        // todo: kad se postavi novi broj u bazi treba azurirati prikaz tj azurirati unos.text
        //broj = aktivnost.broj
        //unos.text = broj.toString()
        // u bazi se nalazi inkrement koji je korisnik odabrao prilikom kreiranja aktivnosti
        // za taj inkrement treba povecati broj
        aktivnost.broj += aktivnost.inkrement
    }

    fun smanjiInkrement2(aktivnost: GlavneAktivnosti) {
        aktivnost.broj -= aktivnost.inkrement
    }

    fun dodajUnos(aktivnost: GlavneAktivnosti) {

    }

    private fun dodajAktivnost(view: View) {
        /* korisniku se prikaze lista vec postojecih aktivnosti u bazi
        *   aktivnost koju odabere se dodaje na pocetnu stranicu */
        val naziviAktivnosti : ArrayList<String> = arrayListOf()
        odabraneAktivnosti = ArrayList<Int>()

        for (el in listaAktivnosti) {
            naziviAktivnosti.add(el.naziv)
        }

        val builder = context?.let { AlertDialog.Builder(it) }
        if (builder != null) {
            builder.setTitle("Odaberite aktivnost")
        }

        // dodajemo checkbox listu
        val checkedItems = null
        if (builder != null) {
            builder.setMultiChoiceItems(naziviAktivnosti.toTypedArray(), checkedItems) { dialog, which, isChecked ->
                // toTypedArray() pretvara arrayList u array jer setMultiChoiceItems prihvata samo array kao argument
                // which sadrzi indeks pozicije selektovanog elementa u listi
                // ako je data aktivnost bila checkirana pa je korisnik uncheckirao box
                // onda uklanjam tu aktivnost iz liste
                if (odabraneAktivnosti.contains(which)) {
                    odabraneAktivnosti.remove(Integer.valueOf(which))
                } else {
                    // ako je korisnik checkirao box, odgovarajucu aktivnost dodajem u listu odabraneAktivnosti
                    odabraneAktivnosti.add(which)
                }
            }
        }

        // dodajem OK i cancel dugmad
        if (builder != null) {
            builder.setPositiveButton("OK") { dialog, which ->
                // sad treba u listu aktivnosti koje cu proslijediti PocetneAktivnostiAdapter-u
                // dodati aktivnosti koje je korisnik odabrao u alert dialog-u
                for (el in odabraneAktivnosti) {
                    val naziv = naziviAktivnosti[el]

                    // ako vec postoji odabrana aktivnost na pocetnoj, insert je nece ponovo dodati
                    val aktivnostZaDodavanje = db.aktivnostiDao().getByNaziv(naziv)
                    db.glavneAktivnostiDao().insert(GlavneAktivnosti(aktivnostZaDodavanje.id, aktivnostZaDodavanje.naziv,
                        db.inkrementalneDao().getBrojByIdAktivnosti(aktivnostZaDodavanje.id),
                        db.inkrementalneDao().getInkrementByIdAktivnosti(aktivnostZaDodavanje.id),
                        db.kolicinskeDao().getKolicinaById(aktivnostZaDodavanje.id),
                        db.mjerneJediniceDao().getByIdAktivnosti(aktivnostZaDodavanje.id),
                        aktivnostZaDodavanje.id_kategorije))

                    dodajListe()
                }

                //dodajListe()

                recyclerViewPocetna.apply {
                    adapter?.notifyDataSetChanged()
                }
            }
        }

        if (builder != null) {
            builder.setNegativeButton("Cancel", null)
        }

        // kreiram i prikazujem alert dialog
        val dialog = builder?.create()
        if (dialog != null) {
            dialog.show()
        }
    }

}