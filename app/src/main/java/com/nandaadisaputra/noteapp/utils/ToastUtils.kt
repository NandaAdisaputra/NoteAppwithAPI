package com.nandaadisaputra.noteapp.utils

import android.content.Context
import android.widget.Toast

/**
 * `ToastUtils` adalah utilitas untuk menampilkan pesan singkat (toast) di layar.
 * Dengan menggunakan objek ini, kita bisa dengan mudah menampilkan toast tanpa harus
 * menulis ulang kode `Toast.makeText()` setiap kali membutuhkannya.
 */
object ToastUtils {

    /**
     * Fungsi untuk menampilkan toast dengan pesan singkat.
     *
     * @param context Konteks dari aktivitas atau aplikasi.
     * @param message Pesan yang akan ditampilkan dalam toast.
     */
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
