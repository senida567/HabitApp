package com.example.projekat.ui.categories

import android.content.ContentValues
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.projekat.AppDatabase
import com.example.projekat.R
import com.example.projekat.entity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Appendable
import kotlin.coroutines.CoroutineContext


class DodajNovuAktivnostFragment(db : AppDatabase, id : Int) : DialogFragment(), CoroutineScope {
    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private var db : AppDatabase
    private var id_ : Int
    private lateinit var btn : Button
    private lateinit var btnIzbrisi : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dodaj_novu_aktivnost, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postaviIzgled(db.tipoviAktivnostiDao().getNazivById(
            db.kategorijeDao().getById(id_).tip
        ))

        view.findViewById<Button>(R.id.izbrisi).text = "NAPUSTI"
        btnIzbrisi = view.findViewById(R.id.izbrisi)
        btnIzbrisi.setOnClickListener {
            dismiss()
        }
        btn = view.findViewById(R.id.novaAktivnost)
        btn.setOnClickListener {
            provjeriUnos()
        }
    }


    open fun postaviIzgled(s : String) {
        if(s == "Inkrementalne") {
            view?.findViewById<TextView>(R.id.mjernaJedinica)?.setVisibility(View.GONE)
            view?.findViewById<EditText>(R.id.editMJ)?.setVisibility(View.GONE)
            view?.findViewById<EditText>(R.id.editKolicina)?.setVisibility(View.GONE)
            view?.findViewById<TextView>(R.id.kolicina)?.setVisibility(View.GONE)
            view?.findViewById<TextView>(R.id.pocetak)?.setVisibility(View.GONE)
            view?.findViewById<EditText>(R.id.editPocetak)?.setVisibility(View.GONE)
            view?.findViewById<TextView>(R.id.kraj)?.setVisibility(View.GONE)
            view?.findViewById<EditText>(R.id.editKraj)?.setVisibility(View.GONE)

        }else if(s == "Kolicinske") {

            view?.findViewById<TextView>(R.id.broj)?.setVisibility(View.GONE)
            view?.findViewById<EditText>(R.id.editBroj)?.setVisibility(View.GONE)
            view?.findViewById<EditText>(R.id.editInkrement)?.setVisibility(View.GONE)
            view?.findViewById<TextView>(R.id.inkrement)?.setVisibility(View.GONE)
            view?.findViewById<TextView>(R.id.pocetak)?.setVisibility(View.GONE)
            view?.findViewById<EditText>(R.id.editPocetak)?.setVisibility(View.GONE)
            view?.findViewById<TextView>(R.id.kraj)?.setVisibility(View.GONE)
            view?.findViewById<EditText>(R.id.editKraj)?.setVisibility(View.GONE)

        }else {

            view?.findViewById<TextView>(R.id.mjernaJedinica)?.setVisibility(View.GONE)
            view?.findViewById<EditText>(R.id.editMJ)?.setVisibility(View.GONE)
            view?.findViewById<EditText>(R.id.editKolicina)?.setVisibility(View.GONE)
            view?.findViewById<TextView>(R.id.kolicina)?.setVisibility(View.GONE)
            view?.findViewById<TextView>(R.id.broj)?.setVisibility(View.GONE)
            view?.findViewById<EditText>(R.id.editBroj)?.setVisibility(View.GONE)
            view?.findViewById<TextView>(R.id.inkrement)?.setVisibility(View.GONE)
            view?.findViewById<EditText>(R.id.editInkrement)?.setVisibility(View.GONE)

        }
    }

    fun provjeriUnos() {
        val tip = db.kategorijeDao().getById(id_).tip
        val naziv = view?.findViewById<EditText>(R.id.editNaziv)?.text.toString()
        val noviIdAktivnosti = db.aktivnostiDao().getLastId()+1

        if(tip == 1) {
            val noviIdInkr = db.inkrementalneDao().getLastId()+1
            val inkrement = view?.findViewById<EditText>(R.id.editInkrement)?.text.toString()
            val broj = view?.findViewById<EditText>(R.id.editBroj)?.text.toString()

            if(TextUtils.isEmpty(naziv) || TextUtils.isEmpty(inkrement) || TextUtils.isEmpty(broj))
                Toast.makeText(requireActivity(), "Morate unijeti tražene podatke!", Toast.LENGTH_LONG).show()
            else {
                launch {
                    db.aktivnostiDao().insert(Aktivnosti(noviIdAktivnosti, naziv, id_))
                    db.inkrementalneDao().insert(Inkrementalne(noviIdInkr, noviIdAktivnosti, inkrement.toInt(), broj.toInt()))
                    zavrsi()
                }
            }
        } else if(tip == 2) {
            val noviIdKol = db.kolicinskeDao().getLastId()+1
            val kolicina = view?.findViewById<EditText>(R.id.editKolicina)?.text.toString()
            val noviIdMJ = db.mjerneJediniceDao().getLastId()+1
            val mjernaJ = view?.findViewById<EditText>(R.id.editMJ)?.text.toString()

            if(TextUtils.isEmpty(naziv) || TextUtils.isEmpty(kolicina) || TextUtils.isEmpty(mjernaJ))
                Toast.makeText(requireActivity(), "Morate unijeti tražene podatke!", Toast.LENGTH_LONG).show()
            else {
                launch {
                    db.aktivnostiDao().insert(Aktivnosti(noviIdAktivnosti, naziv, id_))
                    db.kolicinskeDao().insert(Kolicinske(noviIdKol, noviIdAktivnosti, kolicina.toInt()))
                    db.mjerneJediniceDao().insert(MjerneJedinice(noviIdMJ, naziv, noviIdAktivnosti))
                    zavrsi()
                }
            }
        } else {
            val noviIdVr = db.vremenskeDao().getLastId()+1
            val pocetak = view?.findViewById<EditText>(R.id.editPocetak)?.text.toString()
            val kraj = view?.findViewById<EditText>(R.id.editKraj)?.text.toString()

            if(TextUtils.isEmpty(naziv) || TextUtils.isEmpty(pocetak) || TextUtils.isEmpty(kraj))
                Toast.makeText(requireActivity(), "Morate unijeti tražene podatke!", Toast.LENGTH_LONG).show()
            else {
                launch {
                    db.aktivnostiDao().insert(Aktivnosti(noviIdAktivnosti, naziv, id_))
                    db.vremenskeDao().insert(Vremenske(noviIdVr, noviIdAktivnosti, pocetak, kraj))
                    zavrsi()
                }
            }
        }
    }

    fun zavrsi() {
        var fr = getFragmentManager()?.beginTransaction()
        fr?.replace(
            R.id.fragment_container, AktivnostiFragment(
                db.kategorijeDao().getById(id_),
                db, false
            )
        )
        fr?.addToBackStack(null)
        fr?.commit()
        dismiss()
    }

    init {
        this.db = db
        this.id_ = id
    }

}