package com.nandaadisaputra.noteapp.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.nandaadisaputra.noteapp.BuildConfig

// Object RetrofitClient untuk mengelola instance Retrofit sebagai singleton
object RetrofitClient {

    private const val TIMEOUT = 30L // Timeout koneksi dalam detik

    // Logging interceptor untuk memonitor request dan response (hanya aktif di mode debug)
    private val loggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }
    }

    // Singleton OkHttpClient yang digunakan oleh Retrofit untuk menangani koneksi HTTP
    private val client by lazy {
        OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS) // Waktu maksimum untuk koneksi ke server
            .readTimeout(TIMEOUT, TimeUnit.SECONDS) // Waktu maksimum untuk membaca respons
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS) // Waktu maksimum untuk mengirim request
            .addInterceptor(loggingInterceptor) // Menambahkan logging interceptor untuk debugging
            .build()
    }

    // Singleton instance Retrofit yang digunakan untuk komunikasi dengan API
    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL) // URL dasar API diambil dari BuildConfig
            .addConverterFactory(GsonConverterFactory.create()) // Konverter JSON ke objek Kotlin menggunakan Gson
            .client(client) // Menggunakan OkHttpClient yang sudah dikonfigurasi
            .build()
            .create(ApiService::class.java) // Membuat instance ApiService
    }
}

