package com.example.iKonseptual

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var perkating : CardView
    lateinit var japem : CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        perkating = findViewById(R.id.CardView_Perkara_Penting)
        japem = findViewById(R.id.CardView_Jadwal_Pemeriksaan)

        val id = intent.getIntExtra("id",0).toString()

        if (id == "1"){
            val role = 1
            perkating.setOnClickListener{
                val intent = Intent(
                    this,
                    UjppActivity::class.java
                ).apply {putExtra("id",role)}
                startActivity(intent)
            }
            japem.setOnClickListener{
                val intent = Intent(
                    this,
                    MainActivity2::class.java
                ).apply {putExtra("id",role)}
                startActivity(intent)
            }
        }

        else{
            perkating.setOnClickListener{
                val intent = Intent(
                    this,
                    AjppActivity::class.java
                )
                startActivity(intent)
            }
            japem.setOnClickListener{
                val intent = Intent(
                    this,
                    MainActivity2::class.java
                )
                startActivity(intent)
            }
        }
    }
}