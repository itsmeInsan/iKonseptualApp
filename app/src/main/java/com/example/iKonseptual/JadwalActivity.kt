package com.example.iKonseptual

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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
import java.text.SimpleDateFormat
import java.util.Locale

class JadwalActivity : AppCompatActivity() {

    private lateinit var rincian : CardView
    private lateinit var label : TextView
    private lateinit var icon : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_jadwal)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        

        val id = intent.getIntExtra("id",0).toString()

        label.text = intent.getStringExtra("title")
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val labelDetail = intent.getStringExtra("title_d")
        val labelCreate = intent.getStringExtra("title_c")
        val labelEdit = intent.getStringExtra("title_e")

        val getPenyelidikan = PenyelidikanClient.instance
        getPenyelidikan.getAll().enqueue(object: Callback<List<Datapenyelidikan>>{
            override fun onResponse(call: Call<List<Datapenyelidikan>>, response: Response<List<Datapenyelidikan>>){
                if(response.isSuccessful){
                    val dataList = response.body()
                    if(dataList != null){
                        val adapter = DatapenyelidikanAdapter(dataList)
                        recyclerView.adapter = adapter
                    } else {
                        Log.e("API_ERROR", "Failed to get data: ${response.code()}")
                    }
                }
            }
            override fun onFailure(call: Call<List<Datapenyelidikan>>, t: Throwable) {
                // Handle failure
                Log.e("API_ERROR", "Error fetching data: ${t.message}", t)
            }
        })
        if (id == "1"){
            icon.visibility = View.GONE
            val role = 1
            rincian.setOnClickListener{
                val intent = Intent(
                    this,
                    RincianActivity::class.java
                ).apply {putExtra("title_d",labelDetail);putExtra("id",role)}
                startActivity(intent)
            }
        }
        else{
            icon.visibility = View.VISIBLE
            icon.setOnClickListener {
                val intent = Intent(
                    this,
                    CrtjadwalActivity::class.java
                ).apply {putExtra("title_c",labelCreate);}
                startActivity(intent)
            }
            rincian.setOnClickListener{
                val intent = Intent(
                    this,
                    RincianActivity::class.java
                ).apply {putExtra("title_d",labelDetail);
                    putExtra("title_e",labelEdit)}
                startActivity(intent)
            }
        }
    }
}
class DatapenyelidikanAdapter(private val dataList: List<Datapenyelidikan>) : RecyclerView.Adapter<DatapenyelidikanAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_jadwal, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val namaTextView: TextView = itemView.findViewById(R.id.nama)
        private val jaksaTextView: TextView = itemView.findViewById(R.id.jaksa)
        private val dateTextView: TextView = itemView.findViewById(R.id.date)
        // Add more views as needed

        fun bind(data: Datapenyelidikan) {
            namaTextView.text = data.nama
            jaksaTextView.text = data.Jaksa
            dateTextView.text = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(data.date)
            // Bind other data to views
        }
    }
}
