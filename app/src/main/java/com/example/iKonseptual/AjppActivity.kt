package com.example.iKonseptual

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AjppActivity : AppCompatActivity() {

    lateinit var add_pp : ImageView
    lateinit var to_detail_pp : CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ajpp)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        add_pp = findViewById(R.id.imageView_add_pekating)
        to_detail_pp = findViewById(R.id.CardView_toadpp)

        add_pp.setOnClickListener{
            val intent = Intent(
                this,
                AcppActivity::class.java
            )
            startActivity(intent)
        }
        to_detail_pp.setOnClickListener{
            val intent = Intent(
                this,
                AdppActivity::class.java
            )
            startActivity(intent)
        }
    }
}