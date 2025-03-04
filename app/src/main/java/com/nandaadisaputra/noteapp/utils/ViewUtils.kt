package com.nandaadisaputra.noteapp.utils

import android.view.View
import android.widget.Button

/**
 * **Extension Function** untuk mempermudah manipulasi tampilan (View) di Android.
 * Dengan fungsi ini, kita bisa dengan mudah mengubah visibilitas dan status tombol tanpa kode berulang.
 */

/// **Fungsi untuk menampilkan View**
fun View.show() {
    this.visibility = View.VISIBLE
}

/// **Fungsi untuk menyembunyikan View (menghilangkan elemen dari layout sepenuhnya)**
fun View.hide() {
    this.visibility = View.GONE
}

/// **Fungsi untuk mengaktifkan tombol**
fun Button.enable() {
    this.isEnabled = true
    this.alpha = 1.0f // Opsional: Buat tombol terlihat aktif secara visual
}

/// **Fungsi untuk menonaktifkan tombol**
fun Button.disable() {
    this.isEnabled = false
    this.alpha = 0.5f // Opsional: Buat tombol terlihat lebih redup agar terlihat nonaktif
}
