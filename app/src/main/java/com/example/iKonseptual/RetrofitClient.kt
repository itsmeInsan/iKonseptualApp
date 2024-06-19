package com.example.hahh

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

object PenyelidikanClient{
    private const val BASE_URL = "https://script.google.com/macros/s/AKfycbxTEhxzCkmp-o6QXBVV0nAsyzZA-L53KgE3RvoIT_YkwBo-B6OYyidNf3Ny2sN2PEnGjA/"
    val instance: APIPenyelidikan by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(APIPenyelidikan::class.java)
    }
}