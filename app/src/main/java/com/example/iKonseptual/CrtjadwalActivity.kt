package com.example.iKonseptual

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
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
import java.text.SimpleDateFormat
import java.util.Locale

class CrtjadwalActivity : AppCompatActivity() {
    private lateinit var label: TextView
    private lateinit var inputNama: EditText
    private lateinit var inputPerkara: EditText
    private lateinit var inputTempat: EditText
    private lateinit var inputJaksa: EditText
    private lateinit var inputKeperluan: EditText
    private lateinit var btnPost: Button
    private lateinit var btnDatePicker: Button
    private lateinit var tvDate: TextView
    private val calendar = Calendar.getInstance()
    private var formattedDate: String? = null
    @SuppressLint("MissingInflatedId")
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
        btnDatePicker = findViewById(R.id.btnDate)
        inputTempat = findViewById(R.id.inputTempat)
        inputJaksa = findViewById(R.id.inputJaksa)
        inputKeperluan = findViewById(R.id.inputKeperluan)
        btnPost = findViewById(R.id.button_post_jadwal)
        tvDate = findViewById(R.id.textViewDate)
        label.text = intent.getStringExtra("title_c")
        btnDatePicker.setOnClickListener{
            showDatePicker()
        }
        btnPost.setOnClickListener {
            val nama = inputNama.text.toString().trim()
            val perkara = inputPerkara.text.toString().trim()
//            val calendar = Calendar.getInstance()
//            calendar.set(inputWaktu.year,inputWaktu.month,inputWaktu.dayOfMonth)
            val waktu = formattedDate?: ""
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
                if(label.text == "Buat Jadwal Penyelidikan"){
                    val action = "postPenyelidikan"
                    postjadwal(action,postJadwal)
                } else if(label.text == "Buat Jadwal Penyidikan"){
                    val action = "postPenyidikan"
                    postjadwal(action,postJadwal)
                }
            }
        }
    }

    private fun postjadwal(action:String, jadwal: PenyelidikanPenyidikan) {
        PenyelidikanPenyidikanClient.penyelidikanInstance.post(action,jadwal).enqueue(object : Callback<PenyelidikanPenyidikan> {
            override fun onResponse(
                call: Call<PenyelidikanPenyidikan>,
                response: Response<PenyelidikanPenyidikan>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    Toast.makeText(this@CrtjadwalActivity, "Sukses kirim data", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@CrtjadwalActivity, MainActivity2::class.java)
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
