package com.example.iKonseptual

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
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
import java.text.SimpleDateFormat
import java.util.Locale

class EdtjadwalActivity : AppCompatActivity() {

    lateinit var label: TextView
    private lateinit var editNama: EditText
    private lateinit var editPerkara: EditText
    private lateinit var editTempat: EditText
    private lateinit var editJaksa: EditText
    private lateinit var editKeperluan: EditText
    private lateinit var btnEdit: Button
    private lateinit var tvDate: TextView
    private lateinit var btnEditDate: Button
    private val calendar = Calendar.getInstance()
    private var formattedDate: String? = null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edtjadwal)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        label = findViewById(R.id.label_edit)
        editNama = findViewById(R.id.editNama)
        editPerkara = findViewById(R.id.editPerkara)
        editTempat = findViewById(R.id.editTempat)
        editJaksa = findViewById(R.id.editJaksa)
        editKeperluan = findViewById(R.id.editKeperluan)
        btnEdit = findViewById(R.id.buttonedtJadwal)
        tvDate = findViewById(R.id.tvDate)
        btnEditDate = findViewById(R.id.btnEditDate)

        val intent = intent
        val id = intent.getIntExtra("Id", 0)
        val inputNama = intent.getStringExtra("Nama")
        val inputPerkara = intent.getStringExtra("Perkara")
        val inputWaktu = intent.getStringExtra("Waktu_Pelaksanaan")
        val inputTempat = intent.getStringExtra("Tempat")
        val inputJaksa = intent.getStringExtra("Jaksa_yang_melaksanakan")
        val inputKeperluan = intent.getStringExtra("Keperluan")
        val labelEdit = intent.getStringExtra("title_e")
        label.text = labelEdit
        editNama.setText(inputNama)
        editPerkara.setText(inputPerkara)
        tvDate.setText(inputWaktu)
        editTempat.setText(inputTempat)
        editJaksa.setText(inputJaksa)
        editKeperluan.setText(inputKeperluan)
        btnEditDate.setOnClickListener{
            showDatePicker()
        }
        btnEdit.setOnClickListener {
            val nama = editNama.text.toString().trim()
            val perkara = editPerkara.text.toString().trim()
            val waktu = formattedDate?: ""
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
                Log.d("Edit_JadwalActivity", "idItem: $id")
                Log.d("Edit_JadwalActivity", "body: $editJadwal")
                if(labelEdit == "Edit Rincian Penyelidikan"){
                    editPenyelidikan(editJadwal,id)
                }  else if(labelEdit == "Edit Rincian Penyidikan"){
                    editPenyidikan(editJadwal,id)
                }
            }
        }
    }


    private fun editPenyelidikan(penyelidikan: PenyelidikanPenyidikan,id:Int) {
        PenyelidikanPenyidikanClient.penyelidikanInstance.update("updatePenyelidikan",id, penyelidikan).enqueue(object : Callback<PenyelidikanPenyidikanResponse> {
            override fun onResponse(
                call: Call<PenyelidikanPenyidikanResponse>,
                response: Response<PenyelidikanPenyidikanResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    Log.d("EditJadwalActivity", "responsePenyelidikan: ${response.body()}")
                    Toast.makeText(this@EdtjadwalActivity, "Sukses edit data", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@EdtjadwalActivity, MainActivity2::class.java)
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

    private fun editPenyidikan(penyidikan: PenyelidikanPenyidikan,id:Int) {
        PenyelidikanPenyidikanClient.penyidikanInstance.update("updatePenyidikan",id, penyidikan).enqueue(object : Callback<PenyelidikanPenyidikanResponse> {
            override fun onResponse(
                call: Call<PenyelidikanPenyidikanResponse>,
                response: Response<PenyelidikanPenyidikanResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    Log.d("EditJadwalActivity", "responsePenyidikan: ${response.body()}")
                    Toast.makeText(this@EdtjadwalActivity, "Sukses edit data", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@EdtjadwalActivity, MainActivity2::class.java)
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

    private fun showDatePicker() {
        // Create a DatePickerDialog
        val datePickerDialog = DatePickerDialog(
            this, {DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                // Create a new Calendar instance to hold the selected date
                val selectedDate = Calendar.getInstance()
                // Set the selected date using the values received from the DatePicker dialog
                selectedDate.set(year, monthOfYear, dayOfMonth)
                // Create a SimpleDateFormat to format the date as "dd/MM/yyyy"
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                // Format the selected date into a string
                formattedDate = dateFormat.format(selectedDate.time)
                // Update the TextView to display the selected date with the "Selected Date: " prefix
                tvDate.text = "Selected Date: $formattedDate"
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        // Show the DatePicker dialog
        datePickerDialog.show()
    }

}
