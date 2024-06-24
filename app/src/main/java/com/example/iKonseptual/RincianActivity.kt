package com.example.iKonseptual

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
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

class RincianActivity : AppCompatActivity() {

    lateinit var label_d : TextView
    lateinit var icon_del : ImageView
    lateinit var icon_edt : ImageView
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

        val role = intent.getIntExtra("role", 1).toString()

        icon_del = findViewById(R.id.imageView_delete_jadwal)
        icon_edt = findViewById(R.id.imageView_edit_jadwal)

        label_d = findViewById(R.id.label_detail)
        label_d.text = intent.getStringExtra("title_d")

        nama = findViewById(R.id.name_textView)
        perkara = findViewById(R.id.perkara_textView)
        waktu = findViewById(R.id.waktu_textView)
        tempat = findViewById(R.id.tempat_textView)
        jaksa = findViewById(R.id.jaksatextView)
        keperluan = findViewById(R.id.keperluantextView)
        val intent = intent
        val Nama = intent.getStringExtra("Nama")
        val id = intent.getIntExtra("Id",0)
        val Perkara = intent.getStringExtra("Perkara")
        val Waktu_Pelaksanaan = intent.getStringExtra("Waktu_Pelaksanaan")
        val Tempat = intent.getStringExtra("Tempat")
        val Jaksa_yang_melaksanakan = intent.getStringExtra("Jaksa_yang_melaksanakan")
        val Keperluan = intent.getStringExtra("Keperluan")
        val labelEdit = intent.getStringExtra("title_e")
        nama.text = Nama ?: "N/A"
        perkara.text = Perkara ?: "N/A"
        waktu.text = Waktu_Pelaksanaan ?: "N/A"
        tempat.text = Tempat ?: "N/A"
        jaksa.text = Jaksa_yang_melaksanakan ?: "N/A"
        keperluan.text = Keperluan ?: "N/A"

        Log.d("Rincianactivity","role: $role")
        Log.d("Rincianactivity","role: $intent")
        Log.d("Rincianactivity","idItem: $id")
        if (role == "1"){
            icon_del.visibility = View.GONE
            icon_edt.visibility = View.GONE
        }
        else{
            icon_del.visibility = View.VISIBLE
            icon_edt.visibility = View.VISIBLE
            icon_edt.setOnClickListener{
                val nextSession = Intent(this, EdtjadwalActivity::class.java).apply {
                    putExtra("Id", id)
                    putExtra("Nama", Nama)
                    putExtra("Perkara", Perkara)
                    putExtra("Waktu_Pelaksanaan", Waktu_Pelaksanaan)
                    putExtra("Tempat", Tempat)
                    putExtra("Jaksa_yang_melaksanakan", Jaksa_yang_melaksanakan)
                    putExtra("Keperluan", Keperluan)
                    putExtra("title_e",labelEdit)
                }
                startActivity(nextSession)
                finish()
            }
            Log.d("Rincian", "LabelEdit: $labelEdit")

            icon_del.setOnClickListener{
                val nama = nama.text.toString().trim()
                val perkara = perkara.text.toString().trim()
                val waktu = waktu.text.toString().trim()
                val tempat = tempat.text.toString().trim()
                val jaksa = jaksa.text.toString().trim()
                val keperluan = keperluan.text.toString().trim()
                val body = PenyelidikanPenyidikan(
                    Nama = nama,
                    Perkara = perkara,
                    Waktu_Pelaksanaan = waktu,
                    Tempat_Pelaksanaan = tempat,
                    Jaksa_yang_melaksanakan = jaksa,
                    Keperluan = keperluan
                )
                Log.d("RincianActivity","idItem: $id")
                Log.d("RincianActivity","body: $body")
                if(labelEdit == "Edit Rincian Penyelidikan"){
                    val action = "deletePenyelidikan"
                    deletePenyelidikanPenyidikan(action,body,id)
                } else if(labelEdit == "Edit Rincian Penyidikan"){
                    val action = "deletePenyidikan"
                    deletePenyelidikanPenyidikan(action,body,id)
                }

            }

//            if(labelEdit == "Edit Rincian Penyelidikan"){
//                icon_del.setOnClickListener{
//                    val nama = nama.text.toString().trim()
//                    val perkara = perkara.text.toString().trim()
//                    val waktu = waktu.text.toString().trim()
//                    val tempat = tempat.text.toString().trim()
//                    val jaksa = jaksa.text.toString().trim()
//                    val keperluan = keperluan.text.toString().trim()
//                    val body = PenyelidikanPenyidikan(
//                        Nama = nama,
//                        Perkara = perkara,
//                        Waktu_Pelaksanaan = waktu,
//                        Tempat_Pelaksanaan = tempat,
//                        Jaksa_yang_melaksanakan = jaksa,
//                        Keperluan = keperluan
//                    )
//                    Log.d("RincianActivity","idItem: $id")
//                    Log.d("RincianActivity","body: $body")
//                    deletePenyelidikan(body,id)
//                }
//            } else if(labelEdit == "Edit Rincian Penyidikan"){
//
//            }
        }
    }
    private fun deletePenyelidikanPenyidikan(action:String,penyelidikan: PenyelidikanPenyidikan,id:Int){
        PenyelidikanPenyidikanClient.penyelidikanInstance.delete(action,id,penyelidikan).enqueue(object :
            Callback<PenyelidikanPenyidikanResponse> {
            override fun onResponse(
                call: Call<PenyelidikanPenyidikanResponse>,
                response: Response<PenyelidikanPenyidikanResponse>
            ) {
                if(response.isSuccessful && response.body() != null){
                    Log.d("RincianActivity","idItem: $id")
                    Log.d("RincianActivity","body: $penyelidikan")
                    Log.d("DeleteActivity","response: ${response.body()}")
                    Toast.makeText(this@RincianActivity,"Sukses hapus data", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@RincianActivity, MainActivity2::class.java)
                    startActivity(intent)
                } else{
                    Toast.makeText(this@RincianActivity,"Gagal hapus data: ${response.message()}",
                        Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<PenyelidikanPenyidikanResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@RincianActivity, "Gagal hapus jadwal: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

//    private fun deletePenyidikan(penyelidikan: PenyelidikanPenyidikan,id:Int){
//        PenyelidikanPenyidikanClient.penyelidikanInstance.delete("deletePenyidikan",id,penyelidikan).enqueue(object :
//            Callback<PenyelidikanPenyidikanResponse> {
//            override fun onResponse(
//                call: Call<PenyelidikanPenyidikanResponse>,
//                response: Response<PenyelidikanPenyidikanResponse>
//            ) {
//                if(response.isSuccessful && response.body() != null){
//                    Log.d("RincianActivity","idItem: $id")
//                    Log.d("RincianActivity","body: $penyelidikan")
//                    Log.d("DeleteActivity","response: ${response.body()}")
//                    Toast.makeText(this@RincianActivity,"Sukses hapus data", Toast.LENGTH_SHORT).show()
//                    val intent = Intent(this@RincianActivity, MainActivity2::class.java)
//                    startActivity(intent)
//                } else{
//                    Toast.makeText(this@RincianActivity,"Gagal hapus data: ${response.message()}",
//                        Toast.LENGTH_SHORT).show()
//                }
//            }
//            override fun onFailure(call: Call<PenyelidikanPenyidikanResponse>, t: Throwable) {
//                t.printStackTrace()
//                Toast.makeText(this@RincianActivity, "Gagal hapus jadwal: ${t.message}", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
}