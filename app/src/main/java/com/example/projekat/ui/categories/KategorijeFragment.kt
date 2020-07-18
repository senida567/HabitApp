package com.example.projekat.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projekat.AppDatabase
import com.example.projekat.R
import com.example.projekat.adapter.KategorijeAdapter
import com.example.projekat.entity.Kategorije
import com.example.projekat.entity.TipoviAktivnosti
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.kategorije_fragment.*

class KategorijeFragment(db : AppDatabase, l : List<Kategorije>) ://, tAL : List<TipoviAktivnosti>) :
    Fragment(), KategorijeAdapter.OnElementListener {

    private lateinit var btn : FloatingActionButton
    private var db : AppDatabase
    private var kategorijeList : List<Kategorije>
    //private var tipoviAktivnostiList : List<TipoviAktivnosti>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.kategorije_fragment, container, false)

    // populate the views now that the layout has been inflated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // RecyclerView node initialized here


        recyclerView.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            adapter = KategorijeAdapter(db, kategorijeList,this@KategorijeFragment)
        }

        btn = view.findViewById(R.id.floatingActionButton_kategorije)
        btn.setOnClickListener {
            dodajKategoriju()
        }

    }

   /* companion object {
        fun newInstance(): KategorijeFragment = MainActivity.db?.let { KategorijeFragment(it) }
    }*/

    open fun dodajKategoriju() {
        var fr = getFragmentManager()?.beginTransaction()
        fr?.replace(R.id.fragment_container, DodajKategorijuFragment())
        fr?.addToBackStack(null)
        fr?.commit()
    }

    override fun onElementClick(position: Int) {
        var fr = getFragmentManager()?.beginTransaction()
        fr?.replace(R.id.fragment_container, AktivnostiFragment(kategorijeList.get(position), db))
        fr?.addToBackStack(null)
        fr?.commit()
    }

    init {
        this.db = db
        this.kategorijeList = l
    }

}