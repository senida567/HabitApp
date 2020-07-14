package com.example.projekat.adapter

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projekat.R
import com.example.projekat.entity.Kategorije


class KategorijeAdapter(kategorijeLista: List<Kategorije>, mOnElementListener: OnElementListener) : RecyclerView.Adapter<KategorijeAdapter.KategorijeViewHolder>() {

    private var kategorijeLista: List<Kategorije>
    private lateinit var mOnElementListener : OnElementListener

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
        holder.naziv.setText(kategorije.naziv)

        /*val osob : List<Osobine>? = GlavnaAktivnost.appDatabase?.getOsobineDao()?.getAll()
        var tekst = ""
        if(osob != null) {
            for(o : Osobine in osob) {
                if(o.id_kategorije == kategorije.id) {
                    tekst += " ! "
                    tekst += o.opis
                }
            }
        }
        holder.osobina.setText(tekst)*/
    }

    override fun getItemCount(): Int {
        return kategorijeLista.size
    }

    class KategorijeViewHolder(itemView: View, onElementListener: OnElementListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var naziv: TextView
        var osobina: TextView
        var mOnElementListener: OnElementListener

        override fun onClick(view: View) {
            Log.d(TAG, "onClick: $adapterPosition")
            mOnElementListener.onElementClick(adapterPosition)
        }

        init {
            naziv = itemView.findViewById(R.id.naziv)
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
    }


}

