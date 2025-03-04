package com.nandaadisaputra.noteapp.data.network

import com.nandaadisaputra.noteapp.data.model.AuthResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.http.*

// Interface yang mendefinisikan API service untuk autentikasi pengguna
interface ApiService {

    // Endpoint untuk registrasi pengguna
    @Multipart // Menandakan bahwa permintaan menggunakan multipart form-data
    @POST("exec") // Mengirimkan data ke endpoint "exec" dengan metode POST
    suspend fun register(
        @Part("action") action: RequestBody = "register".toRequestBody(MultipartBody.FORM),
        // Parameter "action" dengan nilai default "register", digunakan oleh backend untuk membedakan jenis request

        @Part("username") username: RequestBody, // Nama pengguna yang akan diregistrasi
        @Part("email") email: RequestBody, // Alamat email pengguna
        @Part("password") password: RequestBody // Kata sandi pengguna
    ): AuthResponse // Mengembalikan respons berupa AuthResponse

    // Endpoint untuk login pengguna
    @Multipart // Menandakan bahwa permintaan menggunakan multipart form-data
    @POST("exec") // Mengirimkan data ke endpoint "exec" dengan metode POST
    suspend fun login(
        @Part("action") action: RequestBody = "login".toRequestBody(MultipartBody.FORM),
        // Parameter "action" dengan nilai default "login", digunakan oleh backend untuk membedakan jenis request

        @Part("email") email: RequestBody, // Alamat email pengguna yang digunakan untuk login
        @Part("password") password: RequestBody // Kata sandi pengguna
    ): AuthResponse // Mengembalikan respons berupa AuthResponse
}
