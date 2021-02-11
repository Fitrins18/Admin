package com.fitri.admin.pku

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.input_mobil.*
import com.fitri.admin.R

class activity_1 : AppCompatActivity(), View.OnClickListener {


    private lateinit var edtNamaMobil : EditText
    private lateinit var edtNoHP : EditText
    private lateinit var edtRute : EditText
    private lateinit var edtFasilitas : EditText
    private lateinit var edtJadwal : EditText
    private lateinit var edtHarga : EditText
    private lateinit var btnSimpan: Button
    private lateinit var listData: ListView
    private lateinit var ref: DatabaseReference
    private lateinit var anggotaList : MutableList<variabel_R_pku>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.input_mobil)

        ref = FirebaseDatabase.getInstance().getReference("anggota")

        edtNamaMobil = findViewById(R.id.edtnama)
        edtNoHP = findViewById(R.id.edtnoHp)
        edtRute = findViewById(R.id.edtrute)
        edtFasilitas = findViewById(R.id.edtfasilitas)
        edtJadwal = findViewById(R.id.edtjadwal)
        edtHarga = findViewById(R.id.edtharga)
        btnSimpan = findViewById(R.id.btn_simpan)
        listData = findViewById(R.id.lv_hasil)

        btnSimpan.setOnClickListener(this)
        anggotaList = mutableListOf()

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (a in snapshot.children){
                        val anggota = a.getValue(variabel_R_pku::class.java)
                        if (anggota !=null){
                            anggotaList.add(anggota)
                        }
                    }

                    val adapter = PkuAdapter( this@activity_1,
                        R.layout.activity_item_mobil, anggotaList)
                    listData.adapter = adapter

                    println("Output : " +anggotaList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@activity_1, "error: "+error, Toast.LENGTH_LONG).show()
            }
        })

        listData.setOnItemClickListener { parent, view, position, id ->
            val anggota = anggotaList.get(position)
            val intent = Intent(this@activity_1, TambahPku::class.java)
            intent.putExtra(TambahPku.EXTRA_ID, anggota.id)
            intent.putExtra(TambahPku.EXTRA_NAMA, anggota.namaMobil)
            startActivity(intent)
        }
    }

    override fun onClick(v: View?) {
        simpanData()
    }

    private fun simpanData(){
        val namaMobil = edtNamaMobil.text.toString().trim()
        val noHP = edtNoHP.text.toString()
        val rute = edtRute.text.toString()
        val fasilitas = edtFasilitas.text.toString().trim()
        val jadwal = edtJadwal.text.toString()
        val harga = edtHarga.text.toString()

        if (namaMobil.isEmpty()or noHP.isEmpty() or rute.isEmpty() or fasilitas.isEmpty()or jadwal.isEmpty()or harga.isEmpty()){
            Toast.makeText( this, "Isi data secara lengkap tidak boleh kosong",
                Toast.LENGTH_SHORT).show()
            return
        }

        val anggotaId = ref.push().key
        val anggota = variabel_R_pku(anggotaId!!, namaMobil, noHP, rute, fasilitas, jadwal, harga)

        ref.child(anggotaId).setValue(anggota).addOnCompleteListener {
            Toast.makeText(
                applicationContext, "Data berhasil ditambahkan",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}