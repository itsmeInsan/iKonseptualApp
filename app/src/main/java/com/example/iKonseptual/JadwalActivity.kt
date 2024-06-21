package com.example.iKonseptual

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
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

class JadwalActivity : AppCompatActivity() {

    lateinit var rincian: CardView
    lateinit var label: TextView
    lateinit var icon: ImageView
    lateinit var nama: TextView
    lateinit var jaksa: TextView
    lateinit var date: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_jadwal)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rincian = findViewById(R.id.CardView_torincian)
        label = findViewById(R.id.label_jadwal)
        icon = findViewById(R.id.tambah_jadwal)
        nama = findViewById(R.id.name_textView)
        jaksa = findViewById(R.id.jaksa_textView)
        date = findViewById(R.id.date_textView)

        val id = intent.getIntExtra("id", 0).toString()

        label.text = intent.getStringExtra("title")

        val labelDetail = intent.getStringExtra("title_d")
        val labelCreate = intent.getStringExtra("title_c")
        val labelEdit = intent.getStringExtra("title_e")

        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val rolepref = sharedPreferences.getInt("role", -1)
        if (rolepref == 1) {
            icon.visibility = View.GONE
            rincian.setOnClickListener {
                val intent = Intent(
                    this,
                    RincianActivity::class.java
                ).apply { putExtra("title_d", labelDetail); putExtra("id", rolepref) }
                startActivity(intent)
                finish()
            }
        } else {
            icon.visibility = View.VISIBLE
            icon.setOnClickListener {
                val intent = Intent(
                    this,
                    CrtjadwalActivity::class.java
                ).apply { putExtra("title_c", labelCreate) }
                startActivity(intent)
                finish()
            }
            rincian.setOnClickListener {
                val intent = Intent(
                    this,
                    RincianActivity::class.java
                ).apply {
                    putExtra("title_d", labelDetail)
                    putExtra("title_e", labelEdit)
                }
                startActivity(intent)
                finish()
            }
        }
        dataJadwal()
    }

    private fun dataJadwal() {
        // Call both penyelidikan and penyidikan APIs and update UI
        fetchPenyelidikanData()
        fetchPenyidikanData()
    }

    private fun fetchPenyelidikanData() {
        PenyelidikanPenyidikanClient.penyelidikanInstance.getAll().enqueue(object : Callback<PenyelidikanPenyidikanResponse> {
            override fun onResponse(
                call: Call<PenyelidikanPenyidikanResponse>,
                response: Response<PenyelidikanPenyidikanResponse>
            ) {
                handleResponse(response)
            }

            override fun onFailure(call: Call<PenyelidikanPenyidikanResponse>, t: Throwable) {
                showError(t.message)
            }
        })
    }

    private fun fetchPenyidikanData() {
        PenyelidikanPenyidikanClient.penyidikanInstance.getAll().enqueue(object : Callback<PenyelidikanPenyidikanResponse> {
            override fun onResponse(
                call: Call<PenyelidikanPenyidikanResponse>,
                response: Response<PenyelidikanPenyidikanResponse>
            ) {
                handleResponse(response)
            }

            override fun onFailure(call: Call<PenyelidikanPenyidikanResponse>, t: Throwable) {
                showError(t.message)
            }
        })
    }

    private fun handleResponse(response: Response<PenyelidikanPenyidikanResponse>) {
        if (response.isSuccessful && response.body() != null) {
            val penyelidikanResponse = response.body()
            penyelidikanResponse?.let {
                if (it.success) {
                    val data = it.data.firstOrNull() // Assuming you want the first item in the list
                    if (data != null) {
                        nama.text = data.Nama
                        jaksa.text = data.Jaksa_yang_melaksanakan
                        date.text = data.Waktu_Pelaksanaan
                        Toast.makeText(this@JadwalActivity, "Sukses ambil data", Toast.LENGTH_SHORT).show()
                    } else {
                        showDataUnavailable()
                    }
                } else {
                    showDataUnavailable()
                }
            }
        } else {
            val errorBody = response.errorBody()?.string() ?: "Unknown error"
            showError("Failed get data: ${response.code()} - $errorBody")
        }
    }

    private fun showDataUnavailable() {
        nama.text = "N/A"
        jaksa.text = "N/A"
        date.text = "N/A"
        Toast.makeText(this@JadwalActivity, "Data tidak tersedia", Toast.LENGTH_SHORT).show()
    }

    private fun showError(message: String?) {
        Toast.makeText(this@JadwalActivity, "Failed get data: $message", Toast.LENGTH_SHORT).show()
    }
}
