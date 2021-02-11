package com.fitri.admin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.fitri.admin.pku.activity_1


class HalamanMobil: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val i = inflater.inflate(R.layout.halaman_mobil,
            container, false)
        val panggilkuliner: Button = i.findViewById(R.id.btntambah)

        panggilkuliner.setOnClickListener  {
            val intent = Intent(activity, activity_1::class.java)
            startActivity(intent)
        }
        return i
    }

}
