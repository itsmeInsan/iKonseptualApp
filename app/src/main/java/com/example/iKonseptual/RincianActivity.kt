package com.example.iKonseptual

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RincianActivity : AppCompatActivity() {

    lateinit var label_d : TextView
    lateinit var icon_del : Button
    lateinit var icon_edt : Button
    lateinit var nama : TextView
    lateinit var perkara: TextView
    lateinit var waktu: TextView
    lateinit var tempat: TextView
    lateinit var jaksa: TextView
    lateinit var keperluan: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rincian)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        label_d = findViewById(R.id.label_detail)
        label_d.text = intent.getStringExtra("title_d")

        nama = findViewById(R.id.name_textView)
        perkara = findViewById(R.id.perkara_textView)
        waktu = findViewById(R.id.waktu_textView)
        tempat = findViewById(R.id.tempat_textView)
        jaksa = findViewById(R.id.jaksatextView)
        keperluan = findViewById(R.id.keperluantextView)
        val intent = intent
        val Nama = intent.getStringExtra("Nama")
        val Perkara = intent.getStringExtra("Perkara")
        val Waktu_Pelaksanaan = intent.getStringExtra("Waktu_Pelaksanaan")
        val Tempat = intent.getStringExtra("Tempat")
        val Jaksa_yang_melaksanakan = intent.getStringExtra("Jaksa_yang_melaksanakan")
        val Keperluan = intent.getStringExtra("Keperluan")

        nama.text = Nama ?: "N/A"
        perkara.text = Perkara ?: "N/A"
        waktu.text = Waktu_Pelaksanaan ?: "N/A"
        tempat.text = Tempat ?: "N/A"
        jaksa.text = Jaksa_yang_melaksanakan ?: "N/A"
        keperluan.text = Keperluan ?: "N/A"

//        val id = intent.getIntExtra("id",0).toString()

        icon_del = findViewById(R.id.imageView_delete_jadwal)
        icon_edt = findViewById(R.id.imageView_edit_jadwal)
//        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
//        val rolepref = sharedPreferences.getInt("role", -1)

//        if (rolepref == 1){
//            icon_del.visibility = View.GONE
//            icon_edt.visibility = View.GONE
//        }
//        else{
//            icon_del.visibility = View.VISIBLE
//            icon_edt.visibility = View.VISIBLE
//
//            val labelEdit = intent.getStringExtra("title_e")
////            getDataById()
//            icon_edt.setOnClickListener{
//                val intent = Intent(
//                    this,
//                    EdtjadwalActivity::class.java
//                ).apply { putExtra("title_e",labelEdit)}
//                startActivity(intent)
//            }
//            icon_del.setOnClickListener{
////                deleteDataPenyelidikan()
//            }
//        }
//
//    }
//
    }
}