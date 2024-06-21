package com.example.iKonseptual

import android.content.Intent
import android.os.Bundle
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

class UjppActivity : AppCompatActivity() {

    private lateinit var det: CardView
    private lateinit var judul: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ujpp)

        // Initialize Views
        det = findViewById(R.id.CardView_det)
        judul = findViewById(R.id.judulPerkaraTextView)

        // Handle Window Insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set CardView Click Listener
        det.setOnClickListener {
            val intent = Intent(this, UdppActivity::class.java)
            startActivity(intent)
        }

        // Fetch Data
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
                        Toast.makeText(this@UjppActivity, "Sukses ambil data", Toast.LENGTH_SHORT).show()
                    } else {
                        judul.text = "N/A"
                        Toast.makeText(this@UjppActivity, "Data tidak tersedia", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Galat"
                    Toast.makeText(this@UjppActivity, "Gagal ambil data: ${response.code()} - $errorBody", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PerkaraPentingResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@UjppActivity, "Failed get data: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
