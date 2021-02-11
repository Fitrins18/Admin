package com.fitri.admin.pku

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.fitri.admin.R

class TambahPku : AppCompatActivity(){
    private lateinit var judul : TextView
    private lateinit var edtNamaMobil : EditText
    private lateinit var edtNoHP : EditText
    private lateinit var edtRute : EditText
    private lateinit var edtFasilitas : EditText
    private lateinit var edtJadwal : EditText
    private lateinit var edtHarga : EditText
    private lateinit var btnSimpan : Button
    private lateinit var lvTambahDetil : ListView
    private lateinit var detilList : MutableList<DetilPku>
    private lateinit var ref : DatabaseReference

    companion object{
        const val EXTRA_ID = "extra_id"
        const val EXTRA_NAMA ="extra_nama"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_detil_mobil)

        judul = findViewById(R.id.txt_judul_detil)
        edtNamaMobil = findViewById(R.id.edt_NamaMobil)
        edtNoHP = findViewById(R.id.edt_NoHp)
        edtRute = findViewById(R.id.edt_rute)
        edtFasilitas = findViewById(R.id.edt_fasilitas)
        edtJadwal = findViewById(R.id.edt_jadwal)
        edtHarga = findViewById(R.id.edt_harga)
        btnSimpan = findViewById(R.id.btn_tambahDetil)
        lvTambahDetil = findViewById(R.id.lv_tambahDetil)

        val id = intent.getStringExtra(EXTRA_ID)
        val nama = intent.getStringExtra(EXTRA_NAMA)

        detilList = mutableListOf()
        ref = FirebaseDatabase.getInstance().getReference("detil anggota")
            .child(id!!)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    detilList.clear()
                    for (a in snapshot.children){
                        val detil = a.getValue(DetilPku::class.java)
                        if (detil != null){
                            detilList.add(detil)
                        }
                    }

                    val adapter = DetilPkuAdapter(this@TambahPku,
                        R.layout.activity_item_detil_mobil, detilList)
                    lvTambahDetil.adapter = adapter

                    println("Output : " + detilList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        btnSimpan.setOnClickListener {
            simpanDetil()
        }
    }

    private fun simpanDetil(){
        val namaMobil = edtNamaMobil.text.toString().trim()
        val noHP = edtNoHP.text.toString()
        val rute = edtRute.text.toString()
        val fasilitas = edtFasilitas.text.toString().trim()
        val jadwal = edtJadwal.text.toString()
        val harga = edtHarga.text.toString()

        if(namaMobil.isEmpty()){
            edtNamaMobil.error = "Isi alat terlebih dahulu"
            return
        }
        if(noHP.isEmpty()){
            edtNoHP.error = "Isi bahan terlebih dahulu"
            return
        }
        if(rute.isEmpty()){
            edtRute.error = "Isi cara terlebih dahulu"
            return
        }
        if(fasilitas.isEmpty()){
            edtFasilitas.error = "Isi cara terlebih dahulu"
            return
        }
        if(jadwal.isEmpty()){
            edtJadwal.error = "Isi cara terlebih dahulu"
            return
        }
        if(harga.isEmpty()){
            edtHarga.error = "Isi cara terlebih dahulu"
            return
        }
        if(rute.isEmpty()){
            edtRute.error = "Isi cara terlebih dahulu"
            return
        }
        val detilId = ref.push().key

        val detil = DetilPku(detilId!!,namaMobil, noHP, rute, fasilitas, jadwal, harga)

        ref.child(detilId).setValue(detil).addOnCompleteListener {
            Toast.makeText(applicationContext, "Informasi tambahan berhasil ditambahkan",
                Toast.LENGTH_SHORT)
                .show()
        }
    }

}