package com.nandaadisaputra.noteapp.utils

/**
 * Kelas `Resource` digunakan untuk membungkus data yang dikembalikan dari proses asynchronous
 * seperti pemanggilan API atau akses ke database. Ini membantu menangani status loading, sukses, dan error.
 *
 * @param T Tipe data yang akan dikembalikan (misalnya `String`, `List<User>`, dll.).
 * @property data Data yang dikembalikan jika request berhasil.
 * @property message Pesan error jika terjadi kesalahan.
 */
sealed class Resource<T>(val data: T? = null, val message: String? = null) {

    /**
     * Kelas `Loading` menunjukkan bahwa proses sedang berjalan.
     * Contoh: Menampilkan loading indicator saat menunggu respon dari server.
     */
    class Loading<T> : Resource<T>()

    /**
     * Kelas `Success` digunakan jika request berhasil dan mengembalikan data.
     * @param data Data yang berhasil diambil dari server atau database.
     */
    class Success<T>(data: T) : Resource<T>(data)

    /**
     * Kelas `Error` digunakan jika terjadi kesalahan.
     * @param message Pesan error yang menjelaskan masalahnya.
     * @param data Opsional: Data terakhir yang bisa tetap ditampilkan meskipun terjadi error.
     */
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}
