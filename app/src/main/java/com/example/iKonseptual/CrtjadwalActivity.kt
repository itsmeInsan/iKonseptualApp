package com.example.iKonseptual

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CrtjadwalActivity : AppCompatActivity() {
    lateinit var label: TextView
    private lateinit var inputNama: EditText
    private lateinit var inputPerkara: EditText
    private lateinit var inputWaktu: EditText
    private lateinit var inputTempat: EditText
    private lateinit var inputJaksa: EditText
    private lateinit var inputKeperluan: EditText
    private lateinit var btnPost: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_crtjadwal)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        label = findViewById(R.id.label_create)
        inputNama = findViewById(R.id.inputNama)
        inputPerkara = findViewById(R.id.inputPerkara)
        inputWaktu = findViewById(R.id.inputWaktu)
        inputTempat = findViewById(R.id.inputTempat)
        inputJaksa = findViewById(R.id.inputJaksa)
        inputKeperluan = findViewById(R.id.inputKeperluan)
        btnPost = findViewById(R.id.button_post_jadwal)
        label.text = intent.getStringExtra("title_c")

        btnPost.setOnClickListener {
            val nama = inputNama.text.toString().trim()
            val perkara = inputPerkara.text.toString().trim()
            val waktu = inputWaktu.text.toString().trim()
            val tempat = inputTempat.text.toString().trim()
            val jaksa = inputJaksa.text.toString().trim()
            val keperluan = inputKeperluan.text.toString().trim()

            if (nama.isEmpty() || perkara.isEmpty() || waktu.isEmpty() || tempat.isEmpty() || jaksa.isEmpty() || keperluan.isEmpty()) {
                Toast.makeText(this, "Harap pastikan sudah terisi semua. Terimakasih", Toast.LENGTH_SHORT).show()
            } else {
                val postJadwal = PenyelidikanPenyidikan(
                    Nama = nama,
                    Perkara = perkara,
                    Waktu_Pelaksanaan = waktu,
                    Tempat_Pelaksanaan = tempat,
                    Jaksa_yang_melaksanakan = jaksa,
                    Keperluan = keperluan
                )
                postPenyelidikanPenyidikan(postJadwal)
            }
        }
    }

    private fun postPenyelidikanPenyidikan(jadwal: PenyelidikanPenyidikan) {
        PenyelidikanPenyidikanClient.instance.post(jadwal).enqueue(object : Callback<PenyelidikanPenyidikan> {
            override fun onResponse(
                call: Call<PenyelidikanPenyidikan>,
                response: Response<PenyelidikanPenyidikan>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    Toast.makeText(this@CrtjadwalActivity, "Sukses kirim data", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@CrtjadwalActivity, JadwalActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    Toast.makeText(this@CrtjadwalActivity, "Gagal kirim jadwal: ${response.code()} - $errorBody", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<PenyelidikanPenyidikan>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@CrtjadwalActivity, "Gagal kirim data Jadwal: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
