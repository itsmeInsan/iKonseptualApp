package com.example.iKonseptual

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AuthClient {
    private const val BASE_URL = "Link_URL_Google_SpreadSheet"
    val instance: APIAuth by lazy {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(APIAuth::class.java)
    }
}

object PenyelidikanPenyidikanClient {
    private const val BASE_URL = "Link_URL_Google_SpreadSheet"
    val gson = GsonBuilder()
        .setLenient()
        .create()
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val penyelidikanInstance: APIPenyelidikan by lazy {
        retrofit.create(APIPenyelidikan::class.java)
    }

    val penyidikanInstance: APIPenyidikan by lazy {
        retrofit.create(APIPenyidikan::class.java)
    }

    val countInstance: APICount by lazy{
        retrofit.create(APICount::class.java)
    }
}

object PerkaraPentingClient{
    private const val BASE_URL = "Link_URL_Google_SpreadSheet"
    val instance: APIPerkaraPenting by lazy{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(APIPerkaraPenting::class.java)
    }
}
