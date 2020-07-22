package com.example.projekat.adapter

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projekat.AppDatabase
import com.example.projekat.R
import com.example.projekat.entity.Kategorije
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable.cancel
import kotlinx.coroutines.launch


class KategorijeAdapter(db : AppDatabase, kategorijeLista: List<Kategorije>, mOnElementListener: OnElementListener) :
    RecyclerView.Adapter<KategorijeAdapter.KategorijeViewHolder>() {

    private var kategorijeLista: List<Kategorije>
    //private var tipoviAktivnostiLista : List<TipoviAktivnosti>
    private var mOnElementListener : OnElementListener
    private var db : AppDatabase

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): KategorijeViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.kategorije_element, parent, false)
        return KategorijeViewHolder(view, mOnElementListener)
    }

    override fun onBindViewHolder(holder: KategorijeViewHolder, position: Int) {
        val kategorije : Kategorije = kategorijeLista.get(position)
        holder.naziv.text = kategorije.naziv
        holder.osobina.text = kategorije.osobina
        CoroutineScope(Dispatchers.Default).launch {
            holder.tip.text = db.tipoviAktivnostiDao().getById(kategorije.tip).naziv
        }


    }

    override fun getItemCount(): Int {
        return kategorijeLista.size
    }

    class KategorijeViewHolder(itemView: View, onElementListener: OnElementListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var naziv : TextView
        var tip : TextView
        var osobina : TextView
        var mOnElementListener: OnElementListener

        override fun onClick(view: View) {
            Log.d(TAG, "onClick: $adapterPosition")
            mOnElementListener.onElementClick(adapterPosition)
        }

        init {
            naziv = itemView.findViewById(R.id.naziv)
            tip = itemView.findViewById(R.id.tip)
            osobina = itemView.findViewById(R.id.osobina)
            mOnElementListener = onElementListener
            itemView.setOnClickListener(this)
        }
    }

    interface OnElementListener{
        fun onElementClick(position : Int);
    }

    init {
        this.kategorijeLista = kategorijeLista
        this.mOnElementListener = mOnElementListener
        this.db = db
    }


}

