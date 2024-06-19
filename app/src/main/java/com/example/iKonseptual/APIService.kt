package com.example.hahh

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.Date

data class User(val namaDepan: String, val namaBelakang: String, val email: String, val sandi: String, val role: Int)
data class Login(val email: String, val sandi: String)
data class ForgetPass(val newPass: String, val confirmPass: String)
data class LoginResponse(
    val success: Boolean,
    val message: String,
    val data: datauser
)
data class datauser(
    val no: Int,
    val namaDepan: String,
    val namaBelakang: String,
    val email: String,
    val sandi: String,
    val role: Int
)
data class Penyelidikan(val nama: String, val perkara: String, val date: Date, val tempat: String,val jaksa: String, val keperluan: String)
data class Datapenyelidikan(
    val no: Int,
    val nama: String,
    val perkara: String,
    val date:Date,
    val tempat: String,
    val Jaksa: String,
    val keperluan: String
)
interface APIAuth {
    @POST("exec?action=register")
    fun register(@Body user: User): Call<User>

    @POST("exec?action=login")
    fun login(@Body user: Login): Call<LoginResponse>

    @POST("exec?action=updatePassword&id={id}")
    fun forgetPassword(@Path("id") id: Int, @Body user: ForgetPass): Call<ForgetPass>
}

interface APIPenyelidikan{
    @GET("exec")
    fun getAll(): Call<List<Datapenyelidikan>>

    @GET("exec?id={id}")
    fun getById(@Path("id") id: Int): Call<List<Datapenyelidikan>>

    @POST("exec?action=insert")
    fun post(@Body penyelidikan:Penyelidikan): Call<Penyelidikan>

    @POST("exec?action=update&id={id}")
    fun update(@Path("id") id: Int,@Body penyelidikan: Penyelidikan): Call<Penyelidikan>

    @POST("exec?action=delete&id={id}")
    fun delete(@Path("id") id:Int, @Body penyelidikan: Penyelidikan): Call<Penyelidikan>

}