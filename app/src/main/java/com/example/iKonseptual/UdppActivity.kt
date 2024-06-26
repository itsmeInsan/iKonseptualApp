package com.example.iKonseptual

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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
        val intent = intent
        val judulPerkara = intent.getStringExtra("Judul_Perkara")
        val identitasTersangka = intent.getStringExtra("Identitas_tersangka")
        val penahanan = intent.getStringExtra("Penahanan")
        val pasal = intent.getStringExtra("Pasal")
        val kasusPosisi = intent.getStringExtra("Kasus_Posisi")

        judulPerkaraTextView.text = judulPerkara ?: "N/A"
        identitasTersangkaTextView.text = identitasTersangka ?: "N/A"
        penahananTextView.text = penahanan ?: "N/A"
        pasalTextView.text = pasal ?: "N/A"
        kasusPosisiTextView.text = kasusPosisi ?: "N/A"


    }
//    private fun dataPerkaraPenting(id:Int){
//        PerkaraPentingClient.instance.getById(id).enqueue(object : Callback<PerkaraPentingResponse>{
//            override fun onResponse(
//                call: Call<PerkaraPentingResponse>,
//                response: Response<PerkaraPentingResponse>
//            ) {
//                if(response.isSuccessful){
//                    val data = response.body()?.data
//                    if(data != null && data.isNotEmpty()){
//                        val fristItem = data[0]
//                        judulPerkaraTextView.text = fristItem.Judul_Perkara
//                        identitasTersangkaTextView.text = fristItem.Identitas_tersangka
//                        penahananTextView.text = fristItem.Penahanan
//                        pasalTextView.text = fristItem.Pasal
//                        kasusPosisiTextView.text = fristItem.Kasus_Posisi
//                    Toast.makeText(this@UdppActivity,"Sukses ambil data",Toast.LENGTH_SHORT).show()
//                    }else{
//                        judulPerkaraTextView.text = "N/A"
//                        identitasTersangkaTextView.text = "N/A"
//                        penahananTextView.text = "N/A"
//                        pasalTextView.text = "N/A"
//                        kasusPosisiTextView.text = "N/A"
//                        Toast.makeText(this@UdppActivity, "Data tidak tersedia", Toast.LENGTH_SHORT).show()
//                    }
//                } else{
//                    Toast.makeText(this@UdppActivity,"Data tidak tersedia", Toast.LENGTH_SHORT).show()
//                }
//            }
//            override fun onFailure(call: Call<PerkaraPentingResponse>, t: Throwable) {
//                t.printStackTrace()
//                Toast.makeText(this@UdppActivity, "Failed get data: ${t.message}", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
}