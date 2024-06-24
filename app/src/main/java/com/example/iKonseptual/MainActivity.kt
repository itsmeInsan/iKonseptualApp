package com.example.iKonseptual

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var perkating : CardView
    lateinit var japem : CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        perkating = findViewById(R.id.CardView_Perkara_Penting)
        perkating.setOnClickListener{
            loadDataInBackground()
        }
        japem = findViewById(R.id.CardView_Jadwal_Pemeriksaan)
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val rolepref = sharedPreferences.getInt("role", 1)
        if (rolepref == 1){
            val role = 1
            japem.setOnClickListener{
                val intent = Intent(this, MainActivity2::class.java).apply {
                    putExtra("id",role)
                }
                startActivity(intent)
            }
        }

        else{
            japem.setOnClickListener{
                val intent = Intent(this, MainActivity2::class.java)
                startActivity(intent)
            }
        }

    }
    private fun loadDataInBackground(){
        loadJadwalPerkara()
    }
    private fun loadJadwalPerkara(){
        PerkaraPentingClient.instance.getAll().enqueue(object : Callback<PerkaraPentingResponse> {
            override fun onResponse(
                call: Call<PerkaraPentingResponse>,
                response: Response<PerkaraPentingResponse>
            ) {
                if(response.isSuccessful){
                    DataRepository.perkaraPentingData = response.body()?.data
                    checkIfAllDataLoadedPerkara()
                }
            }
            override fun onFailure(call: Call<PerkaraPentingResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
    private fun checkIfAllDataLoadedPerkara() {
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val role = sharedPreferences.getInt("role", 1)
        if (DataRepository.perkaraPentingData != null) {
            if (role == 1) {
                val intent = Intent(this, UjppActivity::class.java).apply {
                    putExtra("id",role)
                }
                startActivity(intent)
            } else {
                val intent = Intent(this, AjppActivity::class.java)
                startActivity(intent)
            }
        }
    }
}