package com.nandaadisaputra.noteapp.data.session

import android.content.Context
import android.content.SharedPreferences

/**
 * Kelas SharedPreferencesHelper digunakan untuk menyimpan dan mengelola data session
 * seperti token autentikasi secara lokal pada perangkat pengguna.
 *
 * @param context - Konteks aplikasi untuk mengakses SharedPreferences
 */
class SharedPreferencesHelper(context: Context) {

    // Inisialisasi SharedPreferences dengan mode PRIVATE (hanya bisa diakses oleh aplikasi ini)
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "noteapp_prefs" // Nama file SharedPreferences
        private const val KEY_TOKEN = "auth_token"    // Kunci untuk menyimpan token autentikasi
    }

    /**
     * Menyimpan token autentikasi ke SharedPreferences
     * @param token - Token yang diterima setelah login berhasil
     */
    fun saveToken(token: String) {
        sharedPreferences.edit().putString(KEY_TOKEN, token).apply()
    }

    /**
     * Mengambil token autentikasi yang tersimpan di SharedPreferences
     * @return String? - Token yang tersimpan atau null jika tidak ada
     */
    fun getToken(): String? {
        return sharedPreferences.getString(KEY_TOKEN, null)
    }

    /**
     * Menghapus token autentikasi dari SharedPreferences (logout)
     */
    fun clearToken() {
        sharedPreferences.edit().remove(KEY_TOKEN).apply()
    }
}

