package com.example.hahh

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.iKonseptual.ChangePassActivity
import com.example.iKonseptual.MainActivity
import com.example.iKonseptual.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var forgotPasswordTextView: Button
    private lateinit var registerTextView: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEditText = findViewById(R.id.editTextLoginEmail)
        passwordEditText = findViewById(R.id.editTextLoginPassword)
        loginButton = findViewById(R.id.button_login)
        forgotPasswordTextView = findViewById(R.id.TextView_LupaSandi)
        registerTextView = findViewById(R.id.TextView_DAFTAR)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val sandi = passwordEditText.text.toString().trim()

            if (email.isEmpty() || sandi.isEmpty()) {
                Toast.makeText(this, "Email dan Sandi harus diisi", Toast.LENGTH_SHORT).show()
            } else {
                val loginUser = Login(email = email, sandi = sandi)
                loginUser(loginUser)
            }
        }

        forgotPasswordTextView.setOnClickListener {
            val intent = Intent(this@LoginActivity, ChangePassActivity::class.java)
            startActivity(intent)
        }

        registerTextView.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(user: Login) {
        AuthClient.instance.login(user).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val data = response.body()?.data
                    if(data != null){
                        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("id", data.no.toString())
                        editor.putString("email", data.email)
                        editor.putString("role", data.role.toString())
                        editor.apply()
                    }
                    Toast.makeText(this@LoginActivity, "Login berhasil", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
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