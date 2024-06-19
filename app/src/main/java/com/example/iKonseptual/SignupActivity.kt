package com.example.iKonseptual

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {
    private lateinit var namaDepanEditText: EditText
    private lateinit var namaBelakangEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signUpButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        namaDepanEditText = findViewById(R.id.editTextNamaDepan)
        namaBelakangEditText = findViewById(R.id.editTextNamaBelakang)
        emailEditText = findViewById(R.id.editTextEmailNew)
        passwordEditText = findViewById(R.id.editTextPassword)
        signUpButton = findViewById(R.id.button_signup)

        signUpButton.setOnClickListener {
            val namaDepan = namaDepanEditText.text.toString().trim()
            val namaBelakang = namaBelakangEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val sandi = passwordEditText.text.toString().trim()
            val role = 1
            if (namaDepan.isEmpty() || namaBelakang.isEmpty() || email.isEmpty() || sandi.isEmpty()) {
                Toast.makeText(this, "Harap diisi semuanya", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                val newUser = User(
                    namaDepan = namaDepan,
                    namaBelakang = namaBelakang,
                    email = email,
                    sandi = sandi,
                    role = role
                )
                registerUser(newUser)
            }
        }
    }

    private fun registerUser(user: User) {
        AuthClient.instance.register(user).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@SignupActivity,
                        "Registration Successful",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Navigate to LoginActivity
                    val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    Log.e("SignUpActivity", "Registration Failed: ${response.code()} - $errorBody")
                    Toast.makeText(
                        this@SignupActivity,
                        "Registration Failed: ${response.code()} - $errorBody",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@SignupActivity, "Error: ${t.message}", Toast.LENGTH_SHORT)
                    .show()
                Log.e("SignUpActivity", "Error: ${t.message}", t)
            }
        })
    }
}