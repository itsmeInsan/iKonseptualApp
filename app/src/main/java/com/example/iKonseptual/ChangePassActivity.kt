package com.example.iKonseptual

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePassActivity : AppCompatActivity() {
    lateinit var passLama : EditText
    lateinit var passBaru : EditText
    lateinit var btnChangePass : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_change_pass)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        passLama = findViewById(R.id.editTextPassLama)
        passBaru = findViewById(R.id.editTextPassBaru)
        btnChangePass = findViewById(R.id.button_changePass)

        btnChangePass.setOnClickListener{
            val oldpass = passLama.text.toString().trim()
            val newpass = passBaru.text.toString().trim()
            if(oldpass.isEmpty() || newpass.isEmpty()){
                Toast.makeText(this, "Pastikan sudah terisi semua", Toast.LENGTH_SHORT).show()
            } else{
                val changePass = ForgetPass(sandi = newpass)
                changePass(changePass)
            }
        }
    }
    private fun changePass(user:ForgetPass){
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val id = sharedPreferences.getInt("id", 0)
        AuthClient.instance.forgetPassword(id,user).enqueue(object: Callback<ForgetPass>{
            override fun onResponse(call: Call<ForgetPass>, response: Response<ForgetPass>) {
                if(response.isSuccessful && response.body() != null){
                    Toast.makeText(this@ChangePassActivity,"Sukses ubah sandi", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@ChangePassActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else{
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    Toast.makeText(this@ChangePassActivity, "Gagal ubah sandi: ${response.code()} - $errorBody}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ForgetPass>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@ChangePassActivity, "Gagal ubah sandi: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}