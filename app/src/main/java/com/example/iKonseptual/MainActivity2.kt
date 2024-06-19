package com.example.iKonseptual

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity2 : AppCompatActivity() {

    lateinit var penyidikan : CardView
    lateinit var penyelidikan : CardView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        penyidikan = findViewById(R.id.CardView_PENYIDIKAN)
        penyelidikan = findViewById(R.id.CardView_PENYELIDIKAN)

        val id = intent.getIntExtra("id",0).toString()

        if (id == "1"){
            val role = 1
            penyidikan.setOnClickListener{
                val labelJadwal = "Jadwal Penyidikan"
                val labelDetail = "Detail Jadwal Penyidikan"
                val labelCreate = "Buat Jadwal Penyidikan"
                val labelEdit= "Ubah Jadwal Penyidikan"
                val intent = Intent(
                    this,
                    JadwalActivity::class.java
                ).apply {putExtra("title",labelJadwal);
                    putExtra("title_d",labelDetail);
                    putExtra("title_c",labelCreate);
                    putExtra("title_e",labelEdit);
                    putExtra("id",role)}
                startActivity(intent)
            }
            penyelidikan.setOnClickListener{
                val labelJadwal = "Jadwal Penyelidikan"
                val labelDetail = "Detail Jadwal Penyelidikan"
                val labelCreate = "Buat Jadwal Penyelidikan"
                val labelEdit= "Ubah Jadwal Penyelidikan"
                val intent = Intent(
                    this,
                    JadwalActivity::class.java
                ).apply {putExtra("title",labelJadwal);
                    putExtra("title_d",labelDetail);
                    putExtra("title_c",labelCreate);
                    putExtra("title_e",labelEdit);
                    putExtra("id",role)}
                startActivity(intent)
            }
        }

        else{
            penyidikan.setOnClickListener{
                val labelJadwal = "Jadwal Penyidikan"
                val labelDetail = "Detail Jadwal Penyidikan"
                val labelCreate = "Buat Jadwal Penyidikan"
                val labelEdit= "Ubah Jadwal Penyidikan"
                val intent = Intent(
                    this,
                    JadwalActivity::class.java
                ).apply {putExtra("title",labelJadwal);
                    putExtra("title_d",labelDetail);
                    putExtra("title_c",labelCreate);
                    putExtra("title_e",labelEdit)}
                startActivity(intent)
            }

            penyelidikan.setOnClickListener{
                val labelJadwal = "Jadwal Penyelidikan"
                val labelDetail = "Detail Jadwal Penyelidikan"
                val labelCreate = "Buat Jadwal Penyelidikan"
                val labelEdit= "Ubah Jadwal Penyelidikan"
                val intent = Intent(
                    this,
                    JadwalActivity::class.java
                ).apply {putExtra("title",labelJadwal);
                    putExtra("title_d",labelDetail);
                    putExtra("title_c",labelCreate);
                    putExtra("title_e",labelEdit)}
                startActivity(intent)
            }
        }
    }
}