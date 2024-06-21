package com.example.iKonseptual

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity2 : AppCompatActivity() {

    lateinit var penyidikan: CardView
    lateinit var penyelidikan: CardView

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
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val rolepref = sharedPreferences.getInt("role", -1)

        setupCardViewClickListeners(rolepref)
    }

    private fun setupCardViewClickListeners(role: Int) {
        val roleSpecificExtras: Intent.() -> Unit = {
            if (role == 1) putExtra("id", role)
        }

        penyidikan.setOnClickListener {
            startJadwalActivity(
                "Jadwal Penyidikan",
                "Detail Jadwal Penyidikan",
                "Buat Jadwal Penyidikan",
                "Ubah Jadwal Penyidikan",
                roleSpecificExtras
            )
        }

        penyelidikan.setOnClickListener {
            startJadwalActivity(
                "Jadwal Penyelidikan",
                "Detail Jadwal Penyelidikan",
                "Buat Jadwal Penyelidikan",
                "Ubah Jadwal Penyelidikan",
                roleSpecificExtras
            )
        }
    }

    private fun startJadwalActivity(
        labelJadwal: String,
        labelDetail: String,
        labelCreate: String,
        labelEdit: String,
        extras: Intent.() -> Unit
    ) {
        val intent = Intent(this, JadwalActivity::class.java).apply {
            putExtra("title", labelJadwal)
            putExtra("title_d", labelDetail)
            putExtra("title_c", labelCreate)
            putExtra("title_e", labelEdit)
            extras()
        }
        startActivity(intent)
    }
}
