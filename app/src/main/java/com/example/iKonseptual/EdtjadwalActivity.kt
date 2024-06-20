package com.example.iKonseptual

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class EdtjadwalActivity : AppCompatActivity() {

    lateinit var label : TextView
    private lateinit var editNama : EditText
    private lateinit var editPerkara : EditText
    private lateinit var editWaktu : EditText
    private lateinit var editTempat : EditText
    private lateinit var editJaksa : EditText
    private lateinit var editKeperluan: EditText
    private lateinit var btnEdit: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edtjadwal)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        label = findViewById(R.id.label_edit)
        editNama = findViewById(R.id.editNama)
        editPerkara = findViewById(R.id.editPerkara)
        editWaktu = findViewById(R.id.editWaktu)
        editTempat = findViewById(R.id.editTempat)
        editJaksa = findViewById(R.id.editJaksa)
        editKeperluan = findViewById(R.id.editKeperluan)
        btnEdit = findViewById(R.id.buttonedtJadwal)
        label.text = intent.getStringExtra("title_e")
        btnEdit.setOnClickListener{
            val nama = editNama.text.toString().trim()
            val perkara = editPerkara.text.toString().trim()
            val waktu = editWaktu.text.toString().trim()
            val tempat = editTempat.text.toString().trim()
            val jaksa = editJaksa.text.toString().trim()
            val keperluan = editKeperluan.text.toString().trim()
        }
    }
}