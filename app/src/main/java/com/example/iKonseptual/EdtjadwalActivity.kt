package com.example.iKonseptual

import android.content.Context
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

class EdtjadwalActivity : AppCompatActivity() {

    private lateinit var label: TextView
    private lateinit var editNama: EditText
    private lateinit var editPerkara: EditText
    private lateinit var editWaktu: EditText
    private lateinit var editTempat: EditText
    private lateinit var editJaksa: EditText
    private lateinit var editKeperluan: EditText
    private lateinit var btnEdit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edtjadwal)

        label = findViewById(R.id.label_edit)
        editNama = findViewById(R.id.editNama)
        editPerkara = findViewById(R.id.editPerkara)
        editWaktu = findViewById(R.id.editWaktu)
        editTempat = findViewById(R.id.editTempat)
        editJaksa = findViewById(R.id.editJaksa)
        editKeperluan = findViewById(R.id.editKeperluan)
        btnEdit = findViewById(R.id.buttonedtJadwal)

        getDataPenyelidikan()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        label.text = intent.getStringExtra("title_e")

        btnEdit.setOnClickListener {
            val nama = editNama.text.toString().trim()
            val perkara = editPerkara.text.toString().trim()
            val waktu = editWaktu.text.toString().trim()
            val tempat = editTempat.text.toString().trim()
            val jaksa = editJaksa.text.toString().trim()
            val keperluan = editKeperluan.text.toString().trim()

            if (nama.isEmpty() || perkara.isEmpty() || waktu.isEmpty() || tempat.isEmpty() || jaksa.isEmpty() || keperluan.isEmpty()) {
                Toast.makeText(this, "Harap pastikan sudah terisi semua. Terimakasih", Toast.LENGTH_SHORT).show()
            } else {
                val editJadwal = PenyelidikanPenyidikan(
                    Nama = nama,
                    Perkara = perkara,
                    Waktu_Pelaksanaan = waktu,
                    Tempat_Pelaksanaan = tempat,
                    Jaksa_yang_melaksanakan = jaksa,
                    Keperluan = keperluan
                )
                editPenyelidikan(editJadwal)
            }
        }
    }

    private fun getDataPenyelidikan() {
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val iduser = sharedPreferences.getInt("id", -1)

        PenyelidikanPenyidikanClient.penyelidikanInstance.getById(id = iduser).enqueue(object : Callback<DataPenyelidikanPenyidikan> {
            override fun onResponse(
                call: Call<DataPenyelidikanPenyidikan>,
                response: Response<DataPenyelidikanPenyidikan>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        editNama.setText(data.Nama)
                        editPerkara.setText(data.Perkara)
                        editWaktu.setText(data.Waktu_Pelaksanaan)
                        editTempat.setText(data.Tempat_Pelaksanaan)
                        editJaksa.setText(data.Jaksa_yang_melaksanakan)
                        editKeperluan.setText(data.Keperluan)
                    }
                } else {
                    Toast.makeText(this@EdtjadwalActivity, "Gagal ambil data: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DataPenyelidikanPenyidikan>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@EdtjadwalActivity, "Gagal ambil data: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun editPenyelidikan(jadwal: PenyelidikanPenyidikan) {
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val iduser = sharedPreferences.getInt("id", -1)

        PenyelidikanPenyidikanClient.penyelidikanInstance.update(id = iduser, jadwal).enqueue(object : Callback<PenyelidikanPenyidikanResponse> {
            override fun onResponse(
                call: Call<PenyelidikanPenyidikanResponse>,
                response: Response<PenyelidikanPenyidikanResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    Toast.makeText(this@EdtjadwalActivity, "Sukses edit data", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@EdtjadwalActivity, JadwalActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@EdtjadwalActivity, "Gagal edit jadwal: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PenyelidikanPenyidikanResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@EdtjadwalActivity, "Gagal edit jadwal: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}
