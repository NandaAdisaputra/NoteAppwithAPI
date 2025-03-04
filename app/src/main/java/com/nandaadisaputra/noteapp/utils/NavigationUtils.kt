package com.nandaadisaputra.noteapp.utils

import android.app.Activity
import android.content.Intent

// Object utilitas untuk menangani navigasi antar aktivitas dalam aplikasi
object NavigationUtils {

    /**
     * Fungsi untuk berpindah ke aktivitas lain.
     * @param activity Activity saat ini yang akan berpindah.
     * @param destination Kelas aktivitas tujuan.
     * @param clearStack Jika `true`, semua aktivitas sebelumnya akan dihapus dari stack (tidak bisa kembali).
     */
    fun navigateToActivity(activity: Activity, destination: Class<out Activity>, clearStack: Boolean = false) {
        val intent = Intent(activity, destination) // Membuat intent untuk navigasi ke aktivitas tujuan
        if (clearStack) {
            // FLAG_ACTIVITY_NEW_TASK -> Memulai aktivitas baru di luar task yang lama.
            // FLAG_ACTIVITY_CLEAR_TASK -> Menghapus semua aktivitas sebelumnya dari stack.
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        activity.startActivity(intent) // Menjalankan aktivitas tujuan
        if (clearStack) {
            activity.finish() // Menutup aktivitas saat ini jika clearStack diaktifkan
        }
    }

    /**
     * Fungsi untuk berpindah ke HomeActivity dan menghapus aktivitas sebelumnya.
     * Ini digunakan setelah login atau registrasi agar pengguna langsung masuk ke halaman utama.
     */
    fun navigateToHome(activity: Activity) {
        navigateToActivity(activity, com.nandaadisaputra.noteapp.ui.note.HomeActivity::class.java, clearStack = true)
    }

    /**
     * Fungsi untuk berpindah ke LoginActivity dan menghapus aktivitas sebelumnya.
     * Ini biasanya digunakan saat pengguna logout agar kembali ke halaman login.
     */
    fun navigateToLogin(activity: Activity) {
        navigateToActivity(activity, com.nandaadisaputra.noteapp.ui.auth.LoginActivity::class.java, clearStack = true)
    }
}
