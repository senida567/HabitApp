package com.example.projekat.ui.categories

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.projekat.AppDatabase
import com.example.projekat.R
import com.example.projekat.entity.Aktivnosti
import kotlinx.android.synthetic.main.fragment_dialog.*


class FragmentDialog(db : AppDatabase, id : Int) : DialogFragment() {

    private var db : AppDatabase
    private var id_ : Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnNE.setOnClickListener {
            dismiss()
        }
        btnDA.setOnClickListener {
            Log.d(TAG, "onViewCreated: " + db.kategorijeDao().getById(id_))
            db.kategorijeDao().deleteId(id_)
            Log.d(TAG, "onViewCreated: " + id_)
            Log.d(TAG, "onViewCreated: " + db.kategorijeDao().getById(id_))
            var fr = getFragmentManager()?.beginTransaction()
            fr?.replace(R.id.fragment_container, KategorijeFragment(db))
            fr?.addToBackStack(null)
            fr?.commit()
            dismiss()
        }

    }

    init {
        this.db = db
        this.id_ = id
    }

}