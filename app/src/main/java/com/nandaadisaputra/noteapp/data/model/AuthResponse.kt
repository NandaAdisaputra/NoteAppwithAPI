package com.nandaadisaputra.noteapp.data.model

import com.google.gson.annotations.SerializedName


// Data class untuk merepresentasikan respons dari API autentikasi
data class AuthResponse(
    @SerializedName("code") val code: Int, // Kode status HTTP, misalnya:
    // 201 -> Berhasil (Created)
    // 400 -> Bad Request (Permintaan tidak valid)
    // 500 -> Internal Server Error (Kesalahan server)

    @SerializedName("status") val status: String, // Status respons API:
    // "success" -> Jika permintaan berhasil
    // "error" -> Jika terjadi kesalahan

    @SerializedName("message") val message: String, // Pesan dari server, misalnya:
    // "Login berhasil" jika sukses
    // "Email atau password salah" jika gagal

    @SerializedName("data") val data: UserData? // Data pengguna jika login berhasil
    // Jika login gagal, nilai ini bisa null
)
