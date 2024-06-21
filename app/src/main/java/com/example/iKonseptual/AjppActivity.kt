package com.example.iKonseptual

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AjppActivity : AppCompatActivity() {

    lateinit var add_pp : ImageView
    lateinit var to_detail_pp : CardView
    lateinit var judul : TextView

    @SuppressLint("MissingInflatedId")
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
        judul = findViewById(R.id.judulPerkara)
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
        getAllDataPerkara()
    }
    private fun getAllDataPerkara() {
        PerkaraPentingClient.instance.getAll().enqueue(object : Callback<PerkaraPentingResponse> {
            override fun onResponse(
                call: Call<PerkaraPentingResponse>,
                response: Response<PerkaraPentingResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    if (data != null && data.isNotEmpty()) {
                        val firstItem = data[0]
                        judul.text = firstItem.Judul_Perkara
                        Toast.makeText(this@AjppActivity, "Sukses ambil data", Toast.LENGTH_SHORT).show()
                    } else {
                        judul.text = "N/A"
                        Toast.makeText(this@AjppActivity, "Data tidak tersedia", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Galat"
                    Toast.makeText(this@AjppActivity, "Gagal ambil data: ${response.code()} - $errorBody", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PerkaraPentingResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@AjppActivity, "Failed get data: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}