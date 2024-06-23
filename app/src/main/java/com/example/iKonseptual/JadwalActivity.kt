package com.example.iKonseptual

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
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

class JadwalActivity : AppCompatActivity() {

    lateinit var label: TextView
    lateinit var icon: ImageView
    lateinit var recyclerView: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_jadwal)

        // Initialize Views
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        label = findViewById(R.id.label_jadwal)
        icon = findViewById(R.id.tambah_jadwal)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val id = intent.getIntExtra("id", 0).toString()

        label.text = intent.getStringExtra("title")

        if (id == "1"){
            icon.visibility = View.GONE
            if(label.text == "Jadwal Penyidikan"){
                getAllDataPenyidikanUser()
            }
            else if(label.text == "Jadwal Penyelidikan"){
                getAllDataPenyelidikanUser()
            }
        }
        else if (id == "0"){
            icon.visibility = View.VISIBLE
            if(label.text == "Jadwal Penyidikan"){
                getAllDataPenyidikanAdmin()
            }
            else if(label.text == "Jadwal Penyelidikan"){
                getAllDataPenyelidikanAdmin()
            }
        }

    }

    private fun getAllDataPenyelidikanUser() {
        PenyelidikanPenyidikanClient.penyelidikanInstance.getAll().enqueue(object : Callback<PenyelidikanPenyidikanResponse> {
            override fun onResponse(
                call: Call<PenyelidikanPenyidikanResponse>,
                response: Response<PenyelidikanPenyidikanResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    if (data != null && data.isNotEmpty()) {
                        recyclerView.adapter = PenyelidikanAdapterUser(this@JadwalActivity,data)
                        Toast.makeText(this@JadwalActivity, "Sukses ambil data", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@JadwalActivity, "Data tidak tersedia", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Galat"
                    Toast.makeText(this@JadwalActivity, "Gagal ambil data: ${response.code()} - $errorBody", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PenyelidikanPenyidikanResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@JadwalActivity, "Failed get data: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getAllDataPenyidikanUser() {
        PenyelidikanPenyidikanClient.penyidikanInstance.getAll().enqueue(object : Callback<PenyelidikanPenyidikanResponse> {
            override fun onResponse(
                call: Call<PenyelidikanPenyidikanResponse>,
                response: Response<PenyelidikanPenyidikanResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    if (data != null && data.isNotEmpty()) {
                        recyclerView.adapter = PenyidikanAdapterUser(this@JadwalActivity,data)
                        Toast.makeText(this@JadwalActivity, "Sukses ambil data", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@JadwalActivity, "Data tidak tersedia", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Galat"
                    Toast.makeText(this@JadwalActivity, "Gagal ambil data: ${response.code()} - $errorBody", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PenyelidikanPenyidikanResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@JadwalActivity, "Failed get data: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun getAllDataPenyelidikanAdmin() {
        PenyelidikanPenyidikanClient.penyelidikanInstance.getAll().enqueue(object : Callback<PenyelidikanPenyidikanResponse> {
            override fun onResponse(
                call: Call<PenyelidikanPenyidikanResponse>,
                response: Response<PenyelidikanPenyidikanResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    if (data != null && data.isNotEmpty()) {
                        recyclerView.adapter = PenyelidikanAdapterAdmin(this@JadwalActivity,data)
                        Toast.makeText(this@JadwalActivity, "Sukses ambil data", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@JadwalActivity, "Data tidak tersedia", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Galat"
                    Toast.makeText(this@JadwalActivity, "Gagal ambil data: ${response.code()} - $errorBody", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PenyelidikanPenyidikanResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@JadwalActivity, "Failed get data: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getAllDataPenyidikanAdmin() {
        PenyelidikanPenyidikanClient.penyidikanInstance.getAll().enqueue(object : Callback<PenyelidikanPenyidikanResponse> {
            override fun onResponse(
                call: Call<PenyelidikanPenyidikanResponse>,
                response: Response<PenyelidikanPenyidikanResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    if (data != null && data.isNotEmpty()) {
                        recyclerView.adapter = PenyidikanAdapterAdmin(this@JadwalActivity,data)
                        Toast.makeText(this@JadwalActivity, "Sukses ambil data", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@JadwalActivity, "Data tidak tersedia", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Galat"
                    Toast.makeText(this@JadwalActivity, "Gagal ambil data: ${response.code()} - $errorBody", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PenyelidikanPenyidikanResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@JadwalActivity, "Failed get data: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}