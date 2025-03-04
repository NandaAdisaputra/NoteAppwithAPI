package com.nandaadisaputra.noteapp.ui.note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nandaadisaputra.noteapp.data.repository.AuthRepository
import com.nandaadisaputra.noteapp.utils.Resource

// ViewModel untuk halaman Home, bertanggung jawab mengelola data terkait logout dan token pengguna
class HomeViewModel(private val repository: AuthRepository) : ViewModel() {

    // LiveData untuk menyimpan status logout
    private val _logoutStatus = MutableLiveData<Resource<Boolean>>()

    // LiveData yang bisa diakses dari luar, digunakan oleh UI untuk mengamati status logout
    val logoutStatus: LiveData<Resource<Boolean>> get() = _logoutStatus

    // Fungsi untuk melakukan logout
    fun logout() {
        _logoutStatus.postValue(Resource.Loading()) // Menandakan proses logout sedang berlangsung
        repository.logout() // Memanggil fungsi logout dari repository
        _logoutStatus.postValue(Resource.Success(true)) // Jika berhasil, kirim status sukses
    }

    // Fungsi untuk mendapatkan token pengguna dari repository
    fun getToken(): String? {
        return repository.getToken()
    }
}
