package com.example.iKonseptual

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
data class PenyelidikanPenyidikan(val nama: String, val perkara: String, val date: Date, val tempat: String,val jaksa: String, val keperluan: String)
data class DataPenyelidikanPenyidikan(
    val no: Int,
    val nama: String,
    val perkara: String,
    val date:Date,
    val tempat: String,
    val Jaksa: String,
    val keperluan: String
)

data class PerkaraPenting(val judul: String, val kasusposisi: String, val identitas:String, val pasal:String, val penahanan: String)
data class DataPerkaraPenting(
    val no: Int,
    val judul: String,
    val kasusposisi: String,
    val identitas: String,
    val pasal: String,
    val penahanan: String
)
interface APIAuth {
    @POST("exec?action=register")
    fun register(@Body user: User): Call<User>

    @POST("exec?action=login")
    fun login(@Body user: Login): Call<LoginResponse>

    @POST("exec?action=updatePassword&id={id}")
    fun forgetPassword(@Path("id") id: Int, @Body user: ForgetPass): Call<ForgetPass>
}

interface APIPenyelidikanPenyidikan{
    @GET("exec")
    fun getAll(): Call<List<DataPenyelidikanPenyidikan>>

    @GET("exec?id={id}")
    fun getById(@Path("id") id: Int): Call<List<DataPenyelidikanPenyidikan>>

    @POST("exec?action=insert")
    fun post(@Body penyelidikan:PenyelidikanPenyidikan): Call<PenyelidikanPenyidikan>

    @POST("exec?action=update&id={id}")
    fun update(@Path("id") id: Int,@Body penyelidikan: PenyelidikanPenyidikan): Call<PenyelidikanPenyidikan>

    @POST("exec?action=delete&id={id}")
    fun delete(@Path("id") id:Int, @Body penyelidikan: PenyelidikanPenyidikan): Call<PenyelidikanPenyidikan>

}

interface APIPerkaraPenting{
    @GET("exec")
    fun getAll(): Call<List<DataPerkaraPenting>>

    @GET("exec?id={id}")
    fun getById(@Path("id") id: Int): Call<List<DataPerkaraPenting>>

    @POST("exec?action=insert")
    fun post(@Body paketing:PerkaraPenting): Call<PerkaraPenting>

    @POST("exec?action=update&id={id}")
    fun update(@Path("id") id: Int,@Body paketing: PerkaraPenting): Call<PerkaraPenting>

    @POST("exec?action=delete&id={id}")
    fun delete(@Path("id") id:Int, @Body paketing: PerkaraPenting): Call<PerkaraPenting>
}

