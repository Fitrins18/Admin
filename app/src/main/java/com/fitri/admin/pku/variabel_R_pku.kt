package com.fitri.admin.pku

data class variabel_R_pku (
    val id :String,
    val namaMobil : String,
    val noHP : String,
    val rute : String,
    val fasilitas : String,
    val jadwal : String,
    val harga : String
){
    constructor() : this ("","","","","","","")
}