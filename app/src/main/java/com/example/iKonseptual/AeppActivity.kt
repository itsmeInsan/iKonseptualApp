package com.example.iKonseptual

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AeppActivity : AppCompatActivity() {
    lateinit var editJudul : EditText
    lateinit var editTersangka : EditText
    lateinit var editPenahanan : EditText
    lateinit var editPasal : EditText
    lateinit var editKasus : EditText
    lateinit var btnedit : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_aepp)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        editJudul = findViewById(R.id.editText_Judul)
        editTersangka = findViewById(R.id.editText_Tersangka)
        editPenahanan = findViewById(R.id.editText_Penahanan)
        editPasal = findViewById(R.id.editText_Pasal)
        editKasus = findViewById(R.id.editText_Kasus_Posisi)
        val intent = intent
        val id = intent.getStringExtra("id")?.toInt()
        val judulPerkara = intent.getStringExtra("judul")
        val tersangka = intent.getStringExtra("tersangka")
        val penahanan = intent.getStringExtra("penahanan")
        val pasal = intent.getStringExtra("pasal")
        val kasus = intent.getStringExtra("kasusPosisi")
        editJudul.text = (judulPerkara) as Editable?
        editTersangka.text = (tersangka) as Editable?
        editPenahanan.text = (penahanan) as Editable?
        editPasal.text = (pasal) as Editable?
        editKasus.text = (kasus) as Editable?
        btnedit.setOnClickListener{
            val judul = editJudul.text.toString().trim()
            val tersangka = editTersangka.text.toString().trim()
            val penahanan = editPenahanan.text.toString().trim()
            val pasal = editPenahanan.text.toString().trim()
            val kasus = editKasus.text.toString().trim()

            if(judul.isEmpty() || tersangka.isEmpty() || penahanan.isEmpty() || pasal.isEmpty() || kasus.isEmpty()){
                Toast.makeText(this, "Harap pastikan sudah terisi semua. Terimakasih", Toast.LENGTH_SHORT).show()
            } else{
                val editPerkaraPenting = PerkaraPenting(
                    Judul_Perkara = judul,
                    Kasus_Posisi = kasus,
                    Identitas_tersangka = tersangka,
                    Pasal = pasal,
                    Penahanan = penahanan
                )
                if (id != null) {
                    editPerkara(editPerkaraPenting,id)
                }
            }
        }
    }
    private fun editPerkara(perkaraPenting: PerkaraPenting,id:Int){
        PerkaraPentingClient.instance.update(id,perkaraPenting).enqueue(object : Callback<PerkaraPenting>{
            override fun onResponse(
                call: Call<PerkaraPenting>,
                response: Response<PerkaraPenting>
            ) {
                if(response.isSuccessful && response.body() != null){
                    Toast.makeText(this@AeppActivity,"Sukses edit data", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@AeppActivity, AjppActivity::class.java)
                    startActivity(intent)
                    finish()
                } else{
                    Toast.makeText(this@AeppActivity,"Gagal edit jadwal: ${response.message()}",Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<PerkaraPenting>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@AeppActivity, "Gagal edit jadwal: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}