package com.example.iKonseptual

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class LoginActivity : AppCompatActivity() {

    lateinit var usernameInput : EditText
    lateinit var passwordInput : EditText
    lateinit var daftar : TextView
    lateinit var lupa_sandi : TextView
    lateinit var loginBtn : Button
    lateinit var user : TextView
    lateinit var admin : TextView

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
        user = findViewById(R.id.user)
        admin = findViewById(R.id.admin)

        user.setOnClickListener{
            val role = 1
            val intent = Intent(
                this,
                MainActivity::class.java
            ).apply { putExtra("id",role) }
            startActivity(intent)
        }

        admin.setOnClickListener{
            val role = 0
            val intent = Intent(
                this,
                MainActivity::class.java
            ).apply { putExtra("id",role) }
            startActivity(intent)
        }

        loginBtn.setOnClickListener{
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()
            Log.i("Test Credential","Username : $username and Password: $password")
            val intent = Intent(
                this,
                MainActivity::class.java
            )
            startActivity(intent)
        }

        daftar.setOnClickListener{
            val intent = Intent(
                this,
                signupActivity::class.java
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
}

