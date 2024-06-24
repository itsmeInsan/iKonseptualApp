package com.example.iKonseptual

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

class MainActivity2 : AppCompatActivity() {

    private lateinit var penyidikan: CardView
    private lateinit var penyelidikan: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        penyidikan = findViewById(R.id.CardView_PENYIDIKAN)
        penyelidikan = findViewById(R.id.CardView_PENYELIDIKAN)

        penyidikan.setOnClickListener{
           loadDataPenyidikan()
        }
        penyelidikan.setOnClickListener{
            loadDataPenyelidikan()
        }

    }
    private fun loadDataPenyelidikan(){
        loadJadwalPenyelidikan()
    }
    private fun loadDataPenyidikan(){
        loadJadwalPenyidikan()
    }
    private fun loadJadwalPenyelidikan(){
        PenyelidikanPenyidikanClient.penyelidikanInstance.getAll().enqueue(object :
            Callback<PenyelidikanPenyidikanResponse> {
            override fun onResponse(
                call: Call<PenyelidikanPenyidikanResponse>,
                response: Response<PenyelidikanPenyidikanResponse>
            ) {
                if(response.isSuccessful){
                    DataRepository.penyelidikanData = response.body()?.data
                    checkIfAllDataLoadedPenyelidikan()
                }
            }
            override fun onFailure(call: Call<PenyelidikanPenyidikanResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
    private fun loadJadwalPenyidikan(){
        PenyelidikanPenyidikanClient.penyidikanInstance.getAll().enqueue(object :
            Callback<PenyelidikanPenyidikanResponse> {
            override fun onResponse(
                call: Call<PenyelidikanPenyidikanResponse>,
                response: Response<PenyelidikanPenyidikanResponse>
            ) {
                if(response.isSuccessful){
                    DataRepository.penyidikanData = response.body()?.data
                    checkIfAllDataLoadedPenyidikan()
                }
            }
            override fun onFailure(call: Call<PenyelidikanPenyidikanResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
    private fun checkIfAllDataLoadedPenyidikan(){
        val role = intent.getIntExtra("id", 1).toString()
        val labelJadwal = "Jadwal Penyidikan"
        val labelDetail = "Detail Jadwal Penyidikan"
        val labelCreate = "Buat Jadwal Penyidikan"
        val labelEdit = "Ubah Jadwal Penyidikan"
        if(DataRepository.penyidikanData!=null){
            if(role == "1"){
                val role = 1
                val intent = Intent(this, JadwalActivity::class.java).apply {
                    putExtra("title", labelJadwal)
                    putExtra("title_d", labelDetail)
                    putExtra("title_c", labelCreate)
                    putExtra("title_e", labelEdit)
                    putExtra("id", role)
                }
                startActivity(intent)
            } else{
                val intent = Intent(this, JadwalActivity::class.java).apply {
                    putExtra("title", labelJadwal)
                    putExtra("title_d", labelDetail)
                    putExtra("title_c", labelCreate)
                    putExtra("title_e", labelEdit)
                }
                startActivity(intent)
            }
        }
    }
    private fun checkIfAllDataLoadedPenyelidikan() {
        val role = intent.getIntExtra("id", 1).toString()
        val labelJadwal = "Jadwal Penyelidikan"
        val labelDetail = "Detail Jadwal Penyelidikan"
        val labelCreate = "Buat Jadwal Penyelidikan"
        val labelEdit = "Ubah Jadwal Penyelidikan"
        if (DataRepository.penyelidikanData != null) {
            if(role == "1"){
                val roleuser = 1
                val intent = Intent(this, JadwalActivity::class.java).apply {
                    putExtra("title", labelJadwal)
                    putExtra("title_d", labelDetail)
                    putExtra("title_c", labelCreate)
                    putExtra("title_e", labelEdit)
                    putExtra("id", roleuser)
                }
                startActivity(intent)
                finish()
            } else{
                val intent = Intent(this, JadwalActivity::class.java).apply {
                    putExtra("title", labelJadwal)
                    putExtra("title_d", labelDetail)
                    putExtra("title_c", labelCreate)
                    putExtra("title_e", labelEdit)
                }
                startActivity(intent)
                finish()
            }
        }
    }
}
