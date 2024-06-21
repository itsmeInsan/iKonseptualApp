package com.example.iKonseptual

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdppActivity : AppCompatActivity() {

    lateinit var edit_pp : ImageView
    lateinit var textJudul: TextView
    lateinit var textTersangka: TextView
    lateinit var textPenahanan: TextView
    lateinit var textPasal: TextView
    lateinit var textkasus: TextView
    lateinit var btnDelete: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_adpp)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        edit_pp = findViewById(R.id.imageView_edit_pekating)
        textJudul = findViewById(R.id.text_Judul)
        textTersangka = findViewById(R.id.text_Tersangka)
        textPenahanan = findViewById(R.id.text_Penahanan)
        textPasal = findViewById(R.id.text_Pasal)
        textkasus = findViewById(R.id.text_Kasus_Posisi)
        val id = 2
        paketing(id)

        edit_pp.setOnClickListener{
            val intent = Intent(
                this,
                AeppActivity::class.java
            )
            startActivity(intent)
        }
    }
    private fun paketing(id:Int){
        PerkaraPentingClient.instance.getById(id).enqueue(object: Callback<PerkaraPentingResponse>{
            override fun onResponse(
                call: Call<PerkaraPentingResponse>,
                response: Response<PerkaraPentingResponse>
            ) {
                if(response.isSuccessful){
                    val data = response.body()?.data
                    if(data != null && data.isNotEmpty()){
                        val fristItem = data[0]
                        textJudul.text = fristItem.Judul_Perkara
                        textTersangka.text = fristItem.Identitas_tersangka
                        textPenahanan.text = fristItem.Penahanan
                        textPasal.text = fristItem.Pasal
                        textkasus.text = fristItem.Kasus_Posisi
                        Toast.makeText(this@AdppActivity, "Sukses ambil data", Toast.LENGTH_SHORT).show()
                    } else{
                        textJudul.text = "N/A"
                        textTersangka.text = "N/A"
                        textPenahanan.text = "N/A"
                        textPasal.text = "N/A"
                        textkasus.text = "N/A"
                        Toast.makeText(this@AdppActivity, "Data tidak tersedia", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Galat"
                    Toast.makeText(this@AdppActivity, "Gagal ambil data: ${response.code()} - $errorBody", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<PerkaraPentingResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@AdppActivity, "Failed get data: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}