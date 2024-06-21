package com.example.iKonseptual

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UdppActivity : AppCompatActivity() {
    lateinit var judulPerkaraTextView: TextView
    lateinit var identitasTersangkaTextView: TextView
    lateinit var penahananTextView: TextView
    lateinit var pasalTextView: TextView
    lateinit var kasusPosisiTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_udpp)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        judulPerkaraTextView = findViewById(R.id.judulPerkaraTextView)
        identitasTersangkaTextView = findViewById(R.id.identitasTersangkaTextView)
        penahananTextView = findViewById(R.id.penahananTextView)
        pasalTextView = findViewById(R.id.pasalTextView)
        kasusPosisiTextView = findViewById(R.id.kasusPosisiTextView)
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val id = sharedPreferences.getInt("id", -1)
        dataPerkaraPenting(id)
    }
    private fun dataPerkaraPenting(id:Int){
        PerkaraPentingClient.instance.getById(id).enqueue(object : Callback<DataPerkaraPenting>{
            override fun onResponse(
                call: Call<DataPerkaraPenting>,
                response: Response<DataPerkaraPenting>
            ) {
                if(response.isSuccessful){
                    response.body()?.let {
                        DataPerkaraPenting ->
                        judulPerkaraTextView.text = DataPerkaraPenting.Judul_Perkara
                        identitasTersangkaTextView.text = DataPerkaraPenting.Identitas_tersangka
                        penahananTextView.text = DataPerkaraPenting.Penahanan
                        pasalTextView.text = DataPerkaraPenting.Pasal
                        kasusPosisiTextView.text = DataPerkaraPenting.Kasus_Posisi
                    }
                    Toast.makeText(this@UdppActivity,"Sukses ambil data",Toast.LENGTH_SHORT).show()
                } else{
                    Toast.makeText(this@UdppActivity,"Data tidak tersedia", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<DataPerkaraPenting>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@UdppActivity, "Failed get data: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}