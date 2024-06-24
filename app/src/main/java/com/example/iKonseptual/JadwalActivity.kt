package com.example.iKonseptual

import android.annotation.SuppressLint
import android.content.Intent
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
                if (DataRepository.penyidikanData != null) {
                    setupRecyclerViewPenyidikanUser(DataRepository.penyidikanData!!)
                    Toast.makeText(this, "Data loaded from repository", Toast.LENGTH_SHORT).show()
                } else {
                    // Fetch Data
                    getAllDataPenyidikanUser()
                }
            }
            else if(label.text == "Jadwal Penyelidikan"){
                if (DataRepository.penyelidikanData != null) {
                    setupRecyclerViewPenyelidikanUser(DataRepository.penyelidikanData!!)
                    Toast.makeText(this, "Data loaded from repository", Toast.LENGTH_SHORT).show()
                } else {
                    // Fetch Data
                    getAllDataPenyelidikanUser()
                }
            }
        }
        else if (id == "0"){
            icon.visibility = View.VISIBLE
            val titleCrt = intent.getStringExtra("title_c")

            if(label.text == "Jadwal Penyidikan"){
                icon.setOnClickListener{
                    val intent = Intent(this, CrtjadwalActivity::class.java).apply {
                        putExtra("title_c", titleCrt)
                    }
                    startActivity(intent)
                }

                if (DataRepository.penyidikanData != null) {
                    setupRecyclerViewPenyidikanAdmin(DataRepository.penyidikanData!!)
                    Toast.makeText(this, "Data loaded from repository", Toast.LENGTH_SHORT).show()
                } else {
                    // Fetch Data
                    getAllDataPenyidikanAdmin()
                }
            }
            else if(label.text == "Jadwal Penyelidikan"){
                icon.setOnClickListener{
                    val intent = Intent(this, CrtjadwalActivity::class.java).apply {
                        putExtra("title_c", titleCrt)
                    }
                    startActivity(intent)
                }

                if (DataRepository.penyelidikanData != null) {
                    setupRecyclerViewPenyidikanAdmin(DataRepository.penyelidikanData!!)
                    Toast.makeText(this, "Data loaded from repository", Toast.LENGTH_SHORT).show()
                } else {
                    // Fetch Data
                    getAllDataPenyelidikanAdmin()
                }
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
                        DataRepository.penyelidikanData = data
                        setupRecyclerViewPenyelidikanUser(data)
//                        recyclerView.adapter = PenyelidikanAdapterUser(this@JadwalActivity,data)
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
                        DataRepository.penyidikanData = data
                        setupRecyclerViewPenyidikanUser(data)
//                        recyclerView.adapter = PenyidikanAdapterUser(this@JadwalActivity,data)
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
                        DataRepository.penyelidikanData = data
                        setupRecyclerViewPenyelidikanAdmin(data)
//                        recyclerView.adapter = PenyelidikanAdapterAdmin(this@JadwalActivity,data)
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
                        DataRepository.penyidikanData = data
                        setupRecyclerViewPenyidikanAdmin(data)
//                        recyclerView.adapter = PenyidikanAdapterAdmin(this@JadwalActivity,data)
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
    private fun setupRecyclerViewPenyelidikanUser(data: List<DataPenyelidikanPenyidikan>){
        recyclerView.adapter = PenyelidikanAdapterUser(this,data)
    }
    private fun setupRecyclerViewPenyidikanUser(data: List<DataPenyelidikanPenyidikan>){
        recyclerView.adapter = PenyidikanAdapterUser(this,data)
    }
    private fun setupRecyclerViewPenyelidikanAdmin(data: List<DataPenyelidikanPenyidikan>){
        recyclerView.adapter = PenyelidikanAdapterAdmin(this,data)
    }
    private fun setupRecyclerViewPenyidikanAdmin(data: List<DataPenyelidikanPenyidikan>){
        recyclerView.adapter = PenyidikanAdapterAdmin(this,data)
    }
}