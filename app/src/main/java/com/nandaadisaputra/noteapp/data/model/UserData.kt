package com.nandaadisaputra.noteapp.data.model

import com.google.gson.annotations.SerializedName

// Data class untuk merepresentasikan data pengguna yang diterima dari API
data class UserData(
    @SerializedName("id") val id: Int, // ID unik pengguna dalam sistem

    @SerializedName("username") val username: String, // Nama pengguna (username) yang digunakan untuk login

    @SerializedName("email") val email: String, // Alamat email pengguna

    @SerializedName("token") val token: String? // Token autentikasi yang diberikan setelah login berhasil
    // Token ini digunakan untuk mengakses endpoint yang membutuhkan autentikasi
    // Bisa bernilai null jika registrasi atau login gagal
)
