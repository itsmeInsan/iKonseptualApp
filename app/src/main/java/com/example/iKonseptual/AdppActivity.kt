package com.example.iKonseptual

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

    lateinit var edit_pp: ImageView
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

        // Ensure window insets are set
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        edit_pp = findViewById(R.id.edit_pekating)
        textJudul = findViewById(R.id.text_Judul)
        textTersangka = findViewById(R.id.text_Tersangka)
        textPenahanan = findViewById(R.id.text_Penahanan)
        textPasal = findViewById(R.id.text_Pasal)
        textkasus = findViewById(R.id.text_Kasus_Posisi)
        btnDelete = findViewById(R.id.delete_pekating)


        val intent = intent
        val id = intent.getIntExtra("Id_Perkara",0)
        val judulPerkara = intent.getStringExtra("Judul_Perkara")
        val identitasTersangka = intent.getStringExtra("Identitas_tersangka")
        val penahanan = intent.getStringExtra("Penahanan")
        val pasal = intent.getStringExtra("Pasal")
        val kasusPosisi = intent.getStringExtra("Kasus_Posisi")

        textJudul.text = judulPerkara ?: "N/A"
        textTersangka.text = identitasTersangka ?: "N/A"
        textPenahanan.text = penahanan ?: "N/A"
        textPasal.text = pasal ?: "N/A"
        textkasus.text = kasusPosisi ?: "N/A"


        edit_pp.setOnClickListener {
            val nextSession = Intent(this, AeppActivity::class.java).apply {
                putExtra("id", id)
                putExtra("judul", judulPerkara)
                putExtra("tersangka", identitasTersangka)
                putExtra("penahanan", penahanan)
                putExtra("pasal", pasal)
                putExtra("kasusPosisi", kasusPosisi)
            }
            startActivity(nextSession)
            finish()
        }
        btnDelete.setOnClickListener(){
            val judul = textJudul.text.toString().trim()
            val tersangka = textTersangka.text.toString().trim()
            val penahanan = textPenahanan.text.toString().trim()
            val pasal = textPasal.text.toString().trim()
            val kasus = textkasus.text.toString().trim()
            val body = PerkaraPenting(
                Judul_Perkara = judul,
                Identitas_tersangka = tersangka,
                Penahanan = penahanan,
                Pasal = pasal,
                Kasus_Posisi = kasus
            )
            Log.d("AdppActivity","idItem: $id")
            deletePerkaraPenting(body,id)
        }
    }

    private fun deletePerkaraPenting(perkaraPenting: PerkaraPenting,id:Int){
        PerkaraPentingClient.instance.delete("delete",id,perkaraPenting).enqueue(object : Callback<PerkaraPentingResponse>{
            override fun onResponse(
                call: Call<PerkaraPentingResponse>,
                response: Response<PerkaraPentingResponse>
            ) {
                if(response.isSuccessful && response.body() != null){
                    Log.d("AdppActivity","response: ${response.body()}")
                    Toast.makeText(this@AdppActivity,"Sukses hapus data", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@AdppActivity, AjppActivity::class.java)
                    startActivity(intent)
                } else{
                    Toast.makeText(this@AdppActivity,"Gagal hapus data: ${response.message()}",Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<PerkaraPentingResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@AdppActivity, "Gagal hapus jadwal: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }
}
