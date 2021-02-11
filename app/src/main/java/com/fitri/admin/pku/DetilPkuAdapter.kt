package com.fitri.admin.pku

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.fitri.admin.R
import com.fitri.admin.pku.DetilPku

class DetilPkuAdapter (
    val detilContext: Context,
    val layoutResId: Int,
    val detilList: MutableList<DetilPku>
) : ArrayAdapter<DetilPku>(detilContext, layoutResId, detilList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(detilContext)
        val view: View = layoutInflater.inflate(layoutResId, null)


        val tvNamaMobil = view.findViewById<TextView>(R.id.ou_namamobil)
        val tvNoHP = view.findViewById<TextView>(R.id.ou_noHp)
        val tvRute = view.findViewById<TextView>(R.id.ou_rute)
        val tvFasilitas = view.findViewById<TextView>(R.id.ou_fasilitas)
        val tvJadwal = view.findViewById<TextView>(R.id.ou_jadwal)
        val tvHarga = view.findViewById<TextView>(R.id.ou_harga)

        val detil = detilList[position]

        tvNamaMobil.text = detil.namaMobil
        tvNoHP.text = detil.noHP
        tvRute.text = detil.rute
        tvFasilitas.text = detil.fasilitas
        tvJadwal.text = detil.jadwal
        tvHarga.text = detil.harga
        return view
    }

}