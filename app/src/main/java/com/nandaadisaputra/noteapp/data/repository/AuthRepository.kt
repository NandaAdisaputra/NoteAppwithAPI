package com.nandaadisaputra.noteapp.data.repository

import com.nandaadisaputra.noteapp.data.model.AuthResponse
import com.nandaadisaputra.noteapp.data.network.RetrofitClient
import com.nandaadisaputra.noteapp.data.session.SharedPreferencesHelper
import com.nandaadisaputra.noteapp.utils.Resource
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.IOException
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.MultipartBody

// Kelas AuthRepository untuk mengelola proses autentikasi (register, login, logout)
class AuthRepository(private val sharedPreferencesHelper: SharedPreferencesHelper) {

    /**
     * Fungsi untuk melakukan registrasi pengguna ke server
     * @param username - Data username dalam bentuk RequestBody
     * @param email - Data email dalam bentuk RequestBody
     * @param password - Data password dalam bentuk RequestBody
     * @return Resource<AuthResponse> - Objek yang berisi hasil register (success atau error)
     */
    suspend fun register(
        username: RequestBody,
        email: RequestBody,
        password: RequestBody
    ): Resource<AuthResponse> {
        return try {
            // Membuat RequestBody untuk menentukan aksi "register"
            val actionBody = "register".toRequestBody(MultipartBody.FORM)

            // Melakukan request ke API untuk registrasi
            val response = RetrofitClient.instance.register(
                action = actionBody, // Mengirim parameter action ke server
                username = username,
                email = email,
                password = password
            )

            // Jika kode status HTTP 201 (Created), berarti registrasi berhasil
            if (response.code == 201) {
                Resource.Success(response)
            } else {
                // Jika gagal, mengembalikan pesan error dari server atau pesan default
                Resource.Error(response.message ?: "Terjadi kesalahan!", response)
            }

        } catch (e: HttpException) {
            // Menangani error HTTP dari server (misal: 400, 500)
            Resource.Error("HTTP Error ${e.code()}: ${e.message()}")
        } catch (e: IOException) {
            // Menangani error jika tidak ada koneksi internet
            Resource.Error("Tidak ada koneksi internet")
        } catch (e: Exception) {
            // Menangani error lainnya
            Resource.Error("Kesalahan: ${e.localizedMessage ?: "Unknown error"}")
        }
    }

    /**
     * Fungsi untuk melakukan login pengguna ke server
     * @param email - Data email dalam bentuk RequestBody
     * @param password - Data password dalam bentuk RequestBody
     * @return Resource<AuthResponse> - Objek yang berisi hasil login (success atau error)
     */
    suspend fun login(email: RequestBody, password: RequestBody): Resource<AuthResponse> {
        return try {
            // Membuat RequestBody untuk menentukan aksi "login"
            val actionBody = "login".toRequestBody(MultipartBody.FORM)

            // Melakukan request ke API untuk login
            val response = RetrofitClient.instance.login(
                action = actionBody, // Mengirim parameter action ke server
                email = email,
                password = password
            )

            // Jika kode status HTTP 200 (OK) dan token tersedia, login berhasil
            if (response.code == 200 && response.data?.token != null) {
                // Menyimpan token ke SharedPreferences agar user tetap login
                sharedPreferencesHelper.saveToken(response.data.token)
                Resource.Success(response)
            } else {
                // Jika gagal, mengembalikan pesan error dari server atau pesan default
                Resource.Error(response.message ?: "Login gagal, coba lagi", response)
            }

        } catch (e: HttpException) {
            // Menangani error HTTP dari server
            Resource.Error("HTTP Error ${e.code()}: ${e.message()}")
        } catch (e: IOException) {
            // Menangani error jika tidak ada koneksi internet
            Resource.Error("Tidak ada koneksi internet")
        } catch (e: Exception) {
            // Menangani error lainnya
            Resource.Error("Kesalahan: ${e.localizedMessage ?: "Unknown error"}")
        }
    }

    /**
     * Fungsi untuk melakukan logout
     * Menghapus token yang tersimpan di SharedPreferences
     */
    fun logout() {
        sharedPreferencesHelper.clearToken()
    }

    /**
     * Fungsi untuk mendapatkan token pengguna yang tersimpan di SharedPreferences
     * @return String? - Token yang tersimpan, bisa null jika tidak ada
     */
    fun getToken(): String? {
        return sharedPreferencesHelper.getToken()
    }
}
