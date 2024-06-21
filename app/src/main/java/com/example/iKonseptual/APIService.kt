package com.example.iKonseptual

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

data class User(val namaDepan: String, val namaBelakang: String, val email: String, val sandi: String, val role: Int)
data class Login(val email: String, val sandi: String)
data class ForgetPass(val sandi: String)
data class LoginResponse(
    val success: Boolean,
    val message: String,
    val data: datauser?
)
data class datauser(
    val no: Int,
    val namaDepan: String,
    val namaBelakang: String,
    val email: String,
    val sandi: String,
    val role: Int
)
data class PenyelidikanPenyidikan(val Nama: String, val Perkara: String, val Waktu_Pelaksanaan: String, val Tempat_Pelaksanaan: String,val Jaksa_yang_melaksanakan: String, val Keperluan: String)
data class DataPenyelidikanPenyidikan(
    val no: Int,
    val Nama: String,
    val Perkara: String,
    val Waktu_Pelaksanaan: String,
    val Tempat_Pelaksanaan: String,
    val Jaksa_yang_melaksanakan: String,
    val Keperluan: String
)
data class PenyelidikanPenyidikanResponse(
    val success: Boolean,
    val message: String,
    val data: List<DataPenyelidikanPenyidikan>
)

data class PerkaraPenting(val Judul_Perkara: String, val Kasus_Posisi: String, val Identitas_tersangka:String, val Pasal:String, val Penahanan: String)
data class DataPerkaraPenting(
    val no: Int,
    val Judul_Perkara: String,
    val Kasus_Posisi: String,
    val Identitas_tersangka: String,
    val Pasal: String,
    val Penahanan: String
)
data class PerkaraPentingResponse(
    val success: Boolean,
    val message: String,
    val data: List<DataPerkaraPenting>
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
    @GET("exec?action=getAllPenyelidikan")
    fun getAll(): Call<PenyelidikanPenyidikanResponse>

    @GET("exec?action=getByIdPenyelidikan&id={id}")
    fun getById(@Path("id") id: Int): Call<DataPenyelidikanPenyidikan>

    @POST("exec?action=postPenyelidikan")
    fun post(@Body penyelidikan:PenyelidikanPenyidikan): Call<PenyelidikanPenyidikan>

    @POST("exec?action=updatePenyelidikan&id={id}")
    fun update(@Path("id") id: Int,@Body penyelidikan: PenyelidikanPenyidikan): Call<PenyelidikanPenyidikanResponse>

    @POST("exec?action=deletePenyelidikan&id={id}")
    fun delete(@Path("id") id:Int): Call<PenyelidikanPenyidikanResponse>
}

interface APIPenyidikan{
    @GET("exec?action=getAllPenyidikan")
    fun getAll(): Call<PenyelidikanPenyidikanResponse>

    @GET("exec?action=getByIdPenyidikan&id={id}")
    fun getById(@Path("id") id: Int): Call<DataPenyelidikanPenyidikan>

    @POST("exec?action=postPenyidikan")
    fun post(@Body penyidikan:PenyelidikanPenyidikan): Call<PenyelidikanPenyidikan>

    @POST("exec?action=updatePenyidikan&id={id}")
    fun update(@Path("id") id: Int,@Body penyelidikan: PenyelidikanPenyidikan): Call<PenyelidikanPenyidikanResponse>

    @POST("exec?action=deletePenyidikan&id={id}")
    fun delete(@Path("id") id:Int): Call<PenyelidikanPenyidikanResponse>
}

interface APIPerkaraPenting{
    @GET("exec")
    fun getAll(): Call<PerkaraPentingResponse>

    @GET("exec?id={id}")
    fun getById(@Path("id") id: Int): Call<PerkaraPentingResponse>

    @POST("exec?action=insert")
    fun post(@Body paketing:PerkaraPenting): Call<PerkaraPenting>

    @POST("exec?action=update&id={id}")
    fun update(@Path("id") id: Int,@Body paketing: PerkaraPenting): Call<PerkaraPenting>

    @POST("exec?action=delete&id={id}")
    fun delete(@Path("id") id:Int, @Body paketing: PerkaraPenting): Call<PerkaraPenting>
}

