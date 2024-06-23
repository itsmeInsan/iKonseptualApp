package com.example.iKonseptual

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

data class User(val namaDepan: String, val namaBelakang: String, val email: String, val sandi: String, val role: Int)
data class Login(val email: String, val sandi: String)
data class ForgetPass(val sandi: String)
data class ChangePassResponse(
    val success: Boolean,
    val message: String,
    val data: ForgetPass?
)
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
data class CountDays(
    val Keterangan: String,
    val Value: Int
)
data class CountResponse(
    val success: Boolean,
    val message: String,
    val data: List<CountDays>
)
interface APIAuth {
    @POST("exec?action=register")
    fun register(@Body user: User): Call<User>

    @POST("exec?action=login")
    fun login(@Body user: Login): Call<LoginResponse>

    @POST("exec")
    fun forgetPassword(
        @Query("action") action: String,
        @Query("id") id: Int,
        @Body user: ForgetPass
    ): Call<ChangePassResponse>
}

interface APIPenyelidikan{
    @GET("exec?action=getAllPenyelidikan")
    fun getAll(): Call<PenyelidikanPenyidikanResponse>

    @GET("exec?action=getByIdPenyelidikan&id={id}")
    fun getById(@Path("id") id: Int): Call<DataPenyelidikanPenyidikan>

    @POST("exec")
    fun post(
        @Query("action") action: String,
        @Body penyelidikan:PenyelidikanPenyidikan): Call<PenyelidikanPenyidikan>

    @POST("exec")
    fun update(@Query("action") action: String,
               @Query("id") id: Int,
               @Body penyelidikan: PenyelidikanPenyidikan): Call<PenyelidikanPenyidikanResponse>

    @POST("exec")
    fun delete(@Query("action") action: String,
               @Query("id") id: Int,
               @Body penyelidikan: PenyelidikanPenyidikan): Call<PenyelidikanPenyidikanResponse>
}

interface APIPenyidikan{
    @GET("exec?action=getAllPenyidikan")
    fun getAll(): Call<PenyelidikanPenyidikanResponse>

    @GET("exec?action=getByIdPenyidikan&id={id}")
    fun getById(@Path("id") id: Int): Call<DataPenyelidikanPenyidikan>

    @POST("exec")
    fun post(
        @Query("action") action: String,
        @Body penyidikan:PenyelidikanPenyidikan): Call<PenyelidikanPenyidikan>

    @POST("exec")
    fun update(@Query("action") action: String,
               @Query("id") id: Int,
               @Body penyelidikan: PenyelidikanPenyidikan): Call<PenyelidikanPenyidikanResponse>

    @POST("exec")
    fun delete(@Query("action") action: String,
               @Query("id") id: Int,
               @Body penyelidikan: PenyelidikanPenyidikan): Call<PenyelidikanPenyidikanResponse>
}
interface APICount{
    @GET("exec")
    fun getCountData(
        @Query("action") action: String): Call<CountResponse>
}
interface APIPerkaraPenting{
    @GET("exec")
    fun getAll(): Call<PerkaraPentingResponse>

    @GET("exec?id={id}")
    fun getById(@Path("id") id: Int): Call<PerkaraPentingResponse>

    @POST("exec?action=insert")
    fun post(@Body paketing:PerkaraPenting): Call<PerkaraPenting>

    @POST("exec")
    fun update(@Query("action") action: String,
               @Query("id") id: Int,
               @Body paketing: PerkaraPenting): Call<PerkaraPentingResponse>

    @POST("exec")
    fun delete(@Query("action") action: String,
               @Query("id") id: Int,
               @Body paketing: PerkaraPenting): Call<PerkaraPentingResponse>
}

