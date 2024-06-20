package com.example.iKonseptual

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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

class LoginActivity : AppCompatActivity() {
    private var TAG = "LoginActivity"
    lateinit var usernameInput : EditText
    lateinit var passwordInput : EditText
    lateinit var daftar : TextView
    lateinit var lupa_sandi : TextView
    lateinit var loginBtn : Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    private fun loginUser(user:Login){
        AuthClient.instance.login(user).enqueue(object: Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>){
                if(response.isSuccessful && response.body()!= null){
                    val data = response.body()?.data
                    if(data != null){
                        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        val id = editor.putInt("id", data.no)
                        val email = editor.putString("email", data.email)
                        val role = editor.putInt("role", data.role)
                        editor.apply()
                        Log.d(TAG, "id: $id")
                        Log.d(TAG, "email: $email")
                        Log.d(TAG, "role: $role")
                    }
                    Log.d(TAG,"data: $data")
                    Toast.makeText(this@LoginActivity, "Login berhasil", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else{
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    Toast.makeText(this@LoginActivity, "Login gagal: ${response.code()} - $errorBody}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@LoginActivity, "Login gagal: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}