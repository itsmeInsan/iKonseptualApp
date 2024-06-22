package com.example.iKonseptual

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JadwalActivity : AppCompatActivity() {

    lateinit var rincian: CardView
    lateinit var label: TextView
    lateinit var icon: ImageView
    lateinit var recyclerView: RecyclerView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_jadwal_penyelidikan)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        label = findViewById(R.id.label_jadwal)
        icon = findViewById(R.id.tambah_jadwal)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

//        val id = intent.getIntExtra("id", 0).toString()

        label.text = intent.getStringExtra("title")

        val labelDetail = intent.getStringExtra("title_d")
        val labelCreate = intent.getStringExtra("title_c")
        val labelEdit = intent.getStringExtra("title_e")

//        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
//        val rolepref = sharedPreferences.getInt("role", -1)
//        if (rolepref == 1) {
//            icon.visibility = View.GONE
//            rincian.setOnClickListener {
//                val intent = Intent(
//                    this,
//                    RincianActivity::class.java
//                ).apply { putExtra("title_d", labelDetail); putExtra("id", rolepref) }
//                startActivity(intent)
//                finish()
//            }
//        } else {
//            icon.visibility = View.VISIBLE
//            icon.setOnClickListener {
//                val intent = Intent(
//                    this,
//                    CrtjadwalActivity::class.java
//                ).apply { putExtra("title_c", labelCreate) }
//                startActivity(intent)
//                finish()
//            }
//        }
        if(label.text == "Jadwal Penyidikan"){
            fetchPenyidikanData()
        } else if(label.text == "Jadwal Penyelidikan"){
            fetchPenyelidikanData()
        }
    }

    private fun dataJadwal() {
        // Call both penyelidikan and penyidikan APIs and update UI
        if(label.text == "Jadwal Penyidikan"){
            fetchPenyidikanData()
        } else if(label.text == "Jadwal Penyelidikan"){
            fetchPenyelidikanData()
        }
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
        if (response.isSuccessful) {
            val penyelidikanResponse = response.body()?.data
           if(!penyelidikanResponse.isNullOrEmpty()){
               recyclerView.adapter = PenyelidikanPenyidikanAdapter(penyelidikanResponse)
               val totalitem = penyelidikanResponse.count()
               val sharedPreferences = getSharedPreferences("item", Context.MODE_PRIVATE)
               val editor = sharedPreferences.edit()
               penyelidikanResponse.forEachIndexed { index, item ->
                   editor.putInt("item_id_$index", item.no)
               }
               editor.apply()
               Toast.makeText(this@JadwalActivity,"Sukses ambil data",Toast.LENGTH_SHORT).show()
           } else{
               Toast.makeText(this@JadwalActivity, "Data tidak tersedia", Toast.LENGTH_SHORT).show()
           }
        } else {
            val errorBody = response.errorBody()?.string() ?: "Unknown error"
            showError("Failed get data: ${response.code()} - $errorBody")
        }
    }

    private fun showError(message: String?) {
        Toast.makeText(this@JadwalActivity, "Failed get data: $message", Toast.LENGTH_SHORT).show()
    }
}
