package com.example.iKonseptual

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RincianActivity : AppCompatActivity() {

    lateinit var label_d : TextView
    lateinit var icon_del : Button
    lateinit var icon_edt : Button
    lateinit var nama : TextView
    lateinit var perkara: TextView
    lateinit var waktu: TextView
    lateinit var tempat: TextView
    lateinit var jaksa: TextView
    lateinit var keperluan: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rincian)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        label_d = findViewById(R.id.label_detail)
        nama = findViewById(R.id.name_textView)
        perkara = findViewById(R.id.perkara_textView)
        waktu = findViewById(R.id.waktu_textView)
        tempat = findViewById(R.id.tempat_textView)
        jaksa = findViewById(R.id.jaksatextView)
        keperluan = findViewById(R.id.keperluantextView)

        label_d.text = intent.getStringExtra("title_d")

        val id = intent.getIntExtra("id",0).toString()

        icon_del = findViewById(R.id.imageView_delete_jadwal)
        icon_edt = findViewById(R.id.imageView_edit_jadwal)
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val rolepref = sharedPreferences.getInt("role", -1)

        if (rolepref == 1){
            icon_del.visibility = View.GONE
            icon_edt.visibility = View.GONE
        }
        else{
            icon_del.visibility = View.VISIBLE
            icon_edt.visibility = View.VISIBLE

            val labelEdit = intent.getStringExtra("title_e")
            getDataById()
            icon_edt.setOnClickListener{
                val intent = Intent(
                    this,
                    EdtjadwalActivity::class.java
                ).apply { putExtra("title_e",labelEdit)}
                startActivity(intent)
            }
            icon_del.setOnClickListener{
                deleteDataPenyelidikan()
            }
        }

    }
    private fun deleteDataPenyelidikan() {
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val iduser = sharedPreferences.getInt("id", -1)

        PenyelidikanPenyidikanClient.penyelidikanInstance.delete(iduser).enqueue(object: Callback<PenyelidikanPenyidikanResponse> {
            override fun onResponse(call: Call<PenyelidikanPenyidikanResponse>, response: Response<PenyelidikanPenyidikanResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@RincianActivity, "Sukses hapus data", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@RincianActivity, JadwalActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@RincianActivity, "Gagal hapus data: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PenyelidikanPenyidikanResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@RincianActivity, "Gagal hapus data: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getDataById(){
        val sharedPreferences = getSharedPreferences("item", Context.MODE_PRIVATE)
        val iditem = sharedPreferences.getInt("id", 1)
        PenyelidikanPenyidikanClient.penyelidikanInstance.getById(iditem).enqueue(object: Callback<DataPenyelidikanPenyidikan>{
            override fun onResponse(
                call: Call<DataPenyelidikanPenyidikan>,
                response: Response<DataPenyelidikanPenyidikan>
            ) {
                if(response.isSuccessful){
                    val data = response.body()
                    if(data != null){
                        nama.setText(data.Nama)
                        perkara.setText(data.Perkara)
                        waktu.setText(data.Waktu_Pelaksanaan)
                        tempat.setText(data.Tempat_Pelaksanaan)
                        jaksa.setText(data.Jaksa_yang_melaksanakan)
                        keperluan.setText(data.Keperluan)
                    }
                } else {
                    Toast.makeText(this@RincianActivity, "Gagal ambil data: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DataPenyelidikanPenyidikan>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@RincianActivity, "Gagal ambil data: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}