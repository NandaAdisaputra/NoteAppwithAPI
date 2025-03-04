package com.nandaadisaputra.noteapp.ui.note

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.nandaadisaputra.noteapp.R
import com.nandaadisaputra.noteapp.data.session.SharedPreferencesHelper
import com.nandaadisaputra.noteapp.databinding.ActivityHomeBinding
import com.nandaadisaputra.noteapp.ui.auth.ViewModelFactory
import com.nandaadisaputra.noteapp.utils.NavigationUtils
import com.nandaadisaputra.noteapp.utils.Resource
import com.nandaadisaputra.noteapp.utils.ToastUtils
import com.nandaadisaputra.noteapp.utils.hide
import com.nandaadisaputra.noteapp.utils.show

class HomeActivity : AppCompatActivity() {
    // Variabel untuk binding layout ActivityHomeBinding
    private lateinit var binding: ActivityHomeBinding

    // Inisialisasi SharedPreferencesHelper untuk mengelola sesi pengguna
    private val sharedPreferencesHelper by lazy { SharedPreferencesHelper(this) }

    // Inisialisasi ViewModel menggunakan ViewModelFactory
    private val homeViewModel: HomeViewModel by viewModels {
        ViewModelFactory(sharedPreferencesHelper)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Menghubungkan binding dengan layout
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mengambil token pengguna yang tersimpan untuk mengecek status login
        val token = homeViewModel.getToken()
        if (token != null) {
            Log.d("Auth", "Token yang tersimpan: $token") // Menampilkan token di Logcat (hanya untuk debugging)
        } else {
            Log.d("Auth", "Tidak ada token yang tersimpan.") // Jika tidak ada token, tampilkan log
        }

        checkLoginStatus() // Memeriksa apakah user sudah login
        setupListeners()   // Mengatur aksi tombol logout
        observeLogout()    // Mengamati perubahan status logout
    }

    // Fungsi untuk mengecek status login pengguna
    private fun checkLoginStatus() {
        val token = sharedPreferencesHelper.getToken()
        if (token.isNullOrEmpty()) {
            // Jika token kosong atau null, arahkan pengguna ke halaman login
            NavigationUtils.navigateToLogin(this)
        } else {
            // Jika sudah login, tampilkan pesan selamat datang
            binding.tvWelcome.text = getString(R.string.welcome)
        }
    }

    // Fungsi untuk menangani klik tombol logout
    private fun setupListeners() {
        binding.btnLogout.setOnClickListener {
            homeViewModel.logout() // Memanggil fungsi logout dari ViewModel
        }
    }

    // Fungsi untuk mengamati status logout dari ViewModel
    private fun observeLogout() {
        homeViewModel.logoutStatus.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.show() // Tampilkan loading saat logout berlangsung
                }
                is Resource.Success -> {
                    binding.progressBar.hide() // Sembunyikan loading jika logout berhasil
                    NavigationUtils.navigateToLogin(this) // Arahkan ke halaman login
                }
                is Resource.Error -> {
                    binding.progressBar.hide() // Sembunyikan loading jika terjadi kesalahan
                    ToastUtils.showToast(this, resource.message ?: "Terjadi kesalahan") // Tampilkan pesan error
                }
            }
        }
    }
}
