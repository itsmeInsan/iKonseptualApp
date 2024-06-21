package com.example.iKonseptual

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AuthClient {
    private const val BASE_URL = "https://script.google.com/macros/s/AKfycbymbnJFsLBAq76UZv7cq8fG2rliTpIYmMD93oU8ITmS8pcVWkJ6KhyY62OswYuc5nlwEA/"
    val instance: APIAuth by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(APIAuth::class.java)
    }
}

object PenyelidikanPenyidikanClient {
    private const val BASE_URL = "https://script.google.com/macros/s/AKfycbxTEhxzCkmp-o6QXBVV0nAsyzZA-L53KgE3RvoIT_YkwBo-B6OYyidNf3Ny2sN2PEnGjA/"
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val penyelidikanInstance: APIPenyelidikan by lazy {
        retrofit.create(APIPenyelidikan::class.java)
    }

    val penyidikanInstance: APIPenyidikan by lazy {
        retrofit.create(APIPenyidikan::class.java)
    }
}

object PerkaraPentingClient{
    private const val BASE_URL = " https://script.google.com/macros/s/AKfycbxy3mX1V7gQprYF5HCWhif4MR-fnuwzmxVTzG61dY-IRA-tWR2Tps-JhvmCjCXnbSb5gg/"
    val instance: APIPerkaraPenting by lazy{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(APIPerkaraPenting::class.java)
    }
}

