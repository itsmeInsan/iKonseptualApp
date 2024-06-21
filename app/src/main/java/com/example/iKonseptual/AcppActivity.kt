package com.example.iKonseptual

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AcppActivity : AppCompatActivity() {
    private lateinit var editTextJudul : EditText
    private lateinit var editTextTersangka : EditText
    private lateinit var editTextPenahanan : EditText
    private lateinit var editTextPasal : EditText
    private lateinit var editTextKasusPosisi : EditText
    private lateinit var btnPost : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_acpp)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        editTextJudul = findViewById(R.id.editText_Judul)
        editTextTersangka = findViewById(R.id.editText_Tersangka)
        editTextPenahanan = findViewById(R.id.editText_Penahanan)
        editTextPasal = findViewById(R.id.editText_Pasal)
        editTextKasusPosisi = findViewById(R.id.editText_Kasus_Posisi)
        btnPost = findViewById(R.id.button_signup)
        btnPost.setOnClickListener{
            val judul = editTextJudul.text.toString().trim()
            val tersangka = editTextTersangka.text.toString().trim()
            val penahanan = editTextPenahanan.text.toString().trim()
            val pasal = editTextPasal.text.toString().trim()
            val kasus = editTextKasusPosisi.text.toString().trim()

            if(judul.isEmpty() || tersangka.isEmpty() || penahanan.isEmpty() || pasal.isEmpty() || kasus.isEmpty()){
                Toast.makeText(this,"Harap pastikan sudah terisi semua. Terimakasih", Toast.LENGTH_SHORT).show()
            }else{
                val postPerkaraPenting = PerkaraPenting(
                    Judul_Perkara = judul,
                    Identitas_tersangka = tersangka,
                    Penahanan = penahanan,
                    Pasal =  pasal,
                    Kasus_Posisi = kasus
                )
                postPaketing(postPerkaraPenting)
            }
        }
    }
    private fun postPaketing(paketing:PerkaraPenting){
        PerkaraPentingClient.instance.post(paketing).enqueue(object: Callback<PerkaraPenting>{
            override fun onResponse(
                call: Call<PerkaraPenting>,
                response: Response<PerkaraPenting>
            ) {
                if(response.isSuccessful && response.body() != null){
                    Toast.makeText(this@AcppActivity,"Sukses kirim data", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@AcppActivity,AjppActivity::class.java)
                    startActivity(intent)
                    finish()
                } else{
                    val errorBody = response.errorBody()?.string() ?: "Error"
                    Toast.makeText(this@AcppActivity, "Gagal kirim perkara penting: ${response.code()} - $errorBody",Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<PerkaraPenting>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@AcppActivity, "Gagal kirim perkara penting: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}