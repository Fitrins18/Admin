package com.fitri.admin.pku

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.fitri.admin.R
import com.google.firebase.database.FirebaseDatabase

class PkuAdapter (
    val anggotaContext: Context,
    val layoutResId: Int,
    val anggotaList: List<variabel_R_pku>
) : ArrayAdapter<variabel_R_pku>(anggotaContext, layoutResId, anggotaList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(anggotaContext)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val o_namaMobil: TextView = view.findViewById(R.id.ou_namamobil)
        val o_noHP: TextView = view.findViewById(R.id.ou_noHp)
        val o_rute: TextView = view.findViewById(R.id.ou_rute)
        val o_fasilitas: TextView = view.findViewById(R.id.ou_fasilitas)
        val o_jadwal: TextView = view.findViewById(R.id.ou_jadwal)
        val o_harga: TextView = view.findViewById(R.id.ou_harga)
        val imgEdit: ImageView = view.findViewById(R.id.icn_edit)

        val anggota = anggotaList[position]

        imgEdit.setOnClickListener{
            updateDialog(anggota)
        }
        o_namaMobil.text ="Nama Mobil : " + anggota.namaMobil
        o_noHP.text ="No HP : " + anggota.noHP
        o_rute.text ="Rute : " + anggota.rute
        o_fasilitas.text ="Fasilitas : " + anggota.fasilitas
        o_jadwal.text ="Jadwal : " + anggota.jadwal
        o_harga.text ="Harga : " + anggota.harga

        return view

    }

    private fun updateDialog(anggota: variabel_R_pku){
        val builder = AlertDialog.Builder(anggotaContext)
        builder.setTitle("Update Data")
        val inflater = LayoutInflater.from(anggotaContext)
        val view = inflater.inflate(R.layout.activity_update_mobil, null)

        val  edtNamaMobil = view.findViewById<EditText>(R.id.upNamaMobil)
        val  edtNoHP = view.findViewById<EditText>(R.id.upNoHP)
        val  edtRute = view.findViewById<EditText>(R.id.upRute)
        val  edtFasilitas = view.findViewById<EditText>(R.id.upFasilitas)
        val  edtJadwal = view.findViewById<EditText>(R.id.upJadwal)
        val  edtHarga = view.findViewById<EditText>(R.id.upHarga)

        edtNamaMobil.setText(anggota.namaMobil)
        edtNoHP.setText(anggota.noHP)
        edtRute.setText(anggota.rute)
        edtFasilitas.setText(anggota.fasilitas)
        edtJadwal.setText(anggota.jadwal)
        edtHarga.setText(anggota.harga)

        builder.setView(view)

        builder.setPositiveButton("Ubah") {p0, p1 ->
            val dbAnggota = FirebaseDatabase.getInstance().getReference("anggota")
            val namaMobil = edtNamaMobil.text.toString().trim()
            val noHP = edtNoHP.text.toString().trim()
            val rute = edtRute.text.toString()
            val fasilitas = edtFasilitas.text.toString()
            val jadwal = edtJadwal.text.toString()
            val harga = edtHarga.text.toString()

            if (namaMobil.isEmpty() or noHP.isEmpty() or rute.isEmpty() or fasilitas.isEmpty()or jadwal.isEmpty()or harga.isEmpty()) {
                Toast.makeText(anggotaContext, "Isi data secara lengkap tidak boleh kosong",
                    Toast.LENGTH_SHORT)
                    .show()
                return@setPositiveButton
            }

            val anggota = variabel_R_pku(anggota.id, namaMobil, noHP, rute, fasilitas, jadwal, harga)

            dbAnggota.child(anggota.id).setValue(anggota)
            Toast.makeText(anggotaContext, "Data berhasil di update", Toast.LENGTH_SHORT)
                .show()
        }
        builder.setNeutralButton("Batal") { p0, p1 -> }
        builder.setNegativeButton("Hapus") {p0, p1 ->
            val dbAnggota = FirebaseDatabase.getInstance().getReference( "anggota")
                .child(anggota.id)
            val dbDetil = FirebaseDatabase.getInstance().getReference( " detil anggota")
                .child(anggota.id)

            dbAnggota.removeValue()
            dbDetil.removeValue()

            Toast.makeText(anggotaContext, "Data berhasil di hapus", Toast.LENGTH_SHORT)
                .show()
        }

        val alert = builder.create()
        alert.show()

    }
}