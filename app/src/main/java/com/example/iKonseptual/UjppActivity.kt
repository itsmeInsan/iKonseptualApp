package com.example.iKonseptual

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UjppActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ujpp)

        // Initialize Views
       recyclerView = findViewById(R.id.recyclerView)
       recyclerView.layoutManager = LinearLayoutManager(this)

        // Handle Window Insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
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
                        recyclerView.adapter = PerkaraPentingAdapter(data)
                        Toast.makeText(this@UjppActivity, "Sukses ambil data", Toast.LENGTH_SHORT).show()
                    } else {
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
