package com.example.projekat.ui.categories

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.projekat.R
import com.example.projekat.entity.Kategorije


class DodajAktivnostFragment(kategorije : Kategorije, s : String) : Fragment() {

    private lateinit var btn : Button
    private var s : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

        //postaviIzgled()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_dodaj_aktivnost, container, false)

    // populate the views now that the layout has been inflated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn = view.findViewById(R.id.novaAktivnost)
        btn.setOnClickListener {

            /*  var naziv = view.findViewById<EditText>(R.id.naziv).getText().toString()
            var osob = view.findViewById<EditText>(R.id.osobina).getText().toString()
            if(TextUtils.isEmpty(naziv)) {

                Toast.makeText(requireActivity(), "Unesite naziv kategorije!", Toast.LENGTH_LONG).show();
                return@setOnClickListener;

            } else {

                var db = GlavnaAktivnost.appDatabase
                var broj = db?.getKategorijeDao()?.getLastId()
                var lastId: Int
                if (broj != null) lastId = broj + 1
                else lastId = 1

                var novaKategorija = Kategorije(lastId, naziv, 2, osob != "")
                db?.getKategorijeService()?.saveOrUpdate(novaKategorija)

                if(osob != "") {
                    broj = db?.getOsobineDao()?.getLastId()
                    if (broj != null) lastId = broj + 1
                    else lastId = 1
                    db?.getOsobineService()?.saveOrUpdate(Osobine(lastId, osob, novaKategorija.id))
                }

                var fr = getFragmentManager()?.beginTransaction()
                fr?.replace(R.id.fragment_container, KategorijeFragment())
                fr?.addToBackStack(null)
                fr?.commit()
            }
        }

    }

    open fun postaviIzgled() {
        if(s == "inkrementalna") {
            view?.findViewById<TextView>(R.id.mjernaJedinica)?.setVisibility(View.GONE)
            view?.findViewById<EditText>(R.id.editMJ)?.setVisibility(View.GONE)
            view?.findViewById<EditText>(R.id.editKolicina)?.setVisibility(View.GONE)
            view?.findViewById<TextView>(R.id.kolicina)?.setVisibility(View.GONE)
            view?.findViewById<TextView>(R.id.pocetak)?.setVisibility(View.GONE)
            view?.findViewById<EditText>(R.id.editPocetak)?.setVisibility(View.GONE)
            view?.findViewById<TextView>(R.id.kraj)?.setVisibility(View.GONE)
            view?.findViewById<EditText>(R.id.editKraj)?.setVisibility(View.GONE)

        }else if(s == "kolicinska") {

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

        }*/
        }
    }

    init {
        this.s = s
    }
}