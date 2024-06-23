package com.example.iKonseptual

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class LoginActivity : AppCompatActivity() {

//    private lateinit var binding : ActivityMainBinding

    private var TAG = "LoginActivity"
    lateinit var usernameInput : EditText
    lateinit var passwordInput : EditText
    lateinit var daftar : TextView
    lateinit var lupa_sandi : TextView
    lateinit var loginBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this, Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // Permission is already granted, proceed with your functionality
                }
                else -> {
                    // Request the permission
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        } else {
            // Handle notifications for Android 12 and below without permission request
        }

        createNotificationChannel()
        scheduleNotification()

        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        usernameInput = findViewById(R.id.editTextLoginEmail)
        passwordInput = findViewById(R.id.editTextLoginPassword)
        daftar = findViewById(R.id.TextView_DAFTAR)
        lupa_sandi = findViewById(R.id.TextView_LupaSandi)
        loginBtn = findViewById(R.id.button_login)

        loginBtn.setOnClickListener{
            val email = usernameInput.text.toString().trim()
            val sandi = passwordInput.text.toString().trim()

            if (email.isEmpty() || sandi.isEmpty()) {
                Toast.makeText(this, "Email dan Sandi harus diisi", Toast.LENGTH_SHORT).show()
            } else {
                val loginUser = Login(email = email, sandi = sandi)
                loginUser(loginUser)
            }
        }
        daftar.setOnClickListener{
            val intent = Intent(
                this,
                SignupActivity::class.java
            )
            startActivity(intent)
        }
        lupa_sandi.setOnClickListener{
            val intent = Intent(
                this,
                ChangePassActivity::class.java
            )
            startActivity(intent)
        }
    }

    @SuppressLint("ScheduleExactAlarm")
    private fun scheduleNotification()
    {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Schedule first notification
        val calendar1 = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 8) // 8 AM
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }
        val intent1 = Intent(this, Notification::class.java).apply {
            putExtra("notificationId", 1)
        }
        val pendingIntent1 = PendingIntent.getBroadcast(this, 1, intent1, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar1.timeInMillis, pendingIntent1)
    }

    private fun createNotificationChannel()
    {
        val name = "Notif Channel"
        val desc = "A Description of the Channel"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission is granted. Continue with the action or workflow in your app.
        } else {
            // Explain to the user that the feature is unavailable because the
            // features requires a permission that the user has denied.
        }
    }

    private fun loginUser(user: Login) {
        AuthClient.instance.login(user).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val loginResponse = response.body()!!
                    if (loginResponse.success) {
                        val data = loginResponse.data
                        if (data != null) {
                            val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putInt("id", data.no)
                            editor.putString("email", data.email)
                            editor.putInt("role", data.role)
                            editor.apply()
                            Log.d(TAG, "id: ${data.no}")
                            Log.d(TAG, "email: ${data.email}")
                            Log.d(TAG, "role: ${data.role}")
                        }
                        loadDataInBackground()
//                        Log.d(TAG, "data: $data")
//                        Toast.makeText(this@LoginActivity, "Login berhasil", Toast.LENGTH_SHORT).show()
//                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
//                        startActivity(intent)
//                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "Login gagal: ${loginResponse.message}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    Toast.makeText(this@LoginActivity, "Login gagal: ${response.code()} - $errorBody", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@LoginActivity, "Login gagal: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun loadDataInBackground(){
        loadJadwalPenyelidikan()
        loadJadwalPenyidikan()
        loadJadwalPerkara()
    }
    private fun loadJadwalPenyelidikan(){
        PenyelidikanPenyidikanClient.penyelidikanInstance.getAll().enqueue(object :Callback<PenyelidikanPenyidikanResponse>{
            override fun onResponse(
                call: Call<PenyelidikanPenyidikanResponse>,
                response: Response<PenyelidikanPenyidikanResponse>
            ) {
                if(response.isSuccessful){
                    DataRepository.penyelidikanData = response.body()?.data
                    checkIfAllDataLoaded()
                }
            }
            override fun onFailure(call: Call<PenyelidikanPenyidikanResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
    private fun loadJadwalPenyidikan(){
        PenyelidikanPenyidikanClient.penyidikanInstance.getAll().enqueue(object : Callback<PenyelidikanPenyidikanResponse>{
            override fun onResponse(
                call: Call<PenyelidikanPenyidikanResponse>,
                response: Response<PenyelidikanPenyidikanResponse>
            ) {
                if(response.isSuccessful){
                    DataRepository.penyidikanData = response.body()?.data
                    checkIfAllDataLoaded()
                }
            }
            override fun onFailure(call: Call<PenyelidikanPenyidikanResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
    private fun loadJadwalPerkara(){
        PerkaraPentingClient.instance.getAll().enqueue(object : Callback<PerkaraPentingResponse>{
            override fun onResponse(
                call: Call<PerkaraPentingResponse>,
                response: Response<PerkaraPentingResponse>
            ) {
                if(response.isSuccessful){
                    DataRepository.perkaraPentingData = response.body()?.data
                    checkIfAllDataLoaded()
                }
            }
            override fun onFailure(call: Call<PerkaraPentingResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
    private fun checkIfAllDataLoaded() {
        if (DataRepository.penyelidikanData != null &&
            DataRepository.penyidikanData != null &&
            DataRepository.perkaraPentingData != null) {
            Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}
