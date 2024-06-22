package com.example.iKonseptual

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AdppActivity : AppCompatActivity() {

    lateinit var edit_pp: ImageView
    lateinit var textJudul: TextView
    lateinit var textTersangka: TextView
    lateinit var textPenahanan: TextView
    lateinit var textPasal: TextView
    lateinit var textkasus: TextView
    lateinit var btnDelete: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_adpp)

        // Ensure window insets are set
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        edit_pp = findViewById(R.id.edit_pekating)
        textJudul = findViewById(R.id.text_Judul)
        textTersangka = findViewById(R.id.text_Tersangka)
        textPenahanan = findViewById(R.id.text_Penahanan)
        textPasal = findViewById(R.id.text_Pasal)
        textkasus = findViewById(R.id.text_Kasus_Posisi)
        btnDelete = findViewById(R.id.delete_pekating)


        val intent = intent
        val id = intent.getStringExtra("Id_Perkara")
        val judulPerkara = intent.getStringExtra("Judul_Perkara")
        val identitasTersangka = intent.getStringExtra("Identitas_tersangka")
        val penahanan = intent.getStringExtra("Penahanan")
        val pasal = intent.getStringExtra("Pasal")
        val kasusPosisi = intent.getStringExtra("Kasus_Posisi")

        textJudul.text = judulPerkara ?: "N/A"
        textTersangka.text = identitasTersangka ?: "N/A"
        textPenahanan.text = penahanan ?: "N/A"
        textPasal.text = pasal ?: "N/A"
        textkasus.text = kasusPosisi ?: "N/A"


        edit_pp.setOnClickListener {
            val nextSession = Intent(this, AeppActivity::class.java).apply {
                putExtra("id", id)
                putExtra("judul", judulPerkara)
                putExtra("tersangka", identitasTersangka)
                putExtra("penahanan", penahanan)
                putExtra("pasal", pasal)
                putExtra("kasusPosisi", kasusPosisi)
            }
            startActivity(nextSession)
            finish()
        }
    }
}
