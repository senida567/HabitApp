package com.example.projekat.ui.categories

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projekat.AppDatabase
import com.example.projekat.R
import com.example.projekat.adapter.KategorijeAdapter
import com.example.projekat.entity.Kategorije
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.kategorije_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class KategorijeFragment(db : AppDatabase) :
    Fragment(), KategorijeAdapter.OnElementListener, CoroutineScope {
    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private lateinit var btn : FloatingActionButton
    private var db : AppDatabase
    private var kategorijeList : List<Kategorije>


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

    fun dodajKategoriju() {
        val fr = getFragmentManager()?.beginTransaction()
        fr?.replace(R.id.fragment_container, DodajKategorijuFragment(db))
        fr?.addToBackStack(null)
        fr?.commit()
    }

    override fun onElementClick(position: Int) {
        var fr = getFragmentManager()?.beginTransaction()
        Log.d(TAG, "onElementClick: " + db.kategorijeDao().getById(position))
        Log.d(TAG, "onElementClick: " + kategorijeList.get(position))
        fr?.replace(R.id.fragment_container, AktivnostiFragment(kategorijeList.get(position), db, false))
        fr?.addToBackStack(null)
        fr?.commit()
    }

    init {
        this.db = db
        this.kategorijeList = db.kategorijeDao().getAll()
    }


}