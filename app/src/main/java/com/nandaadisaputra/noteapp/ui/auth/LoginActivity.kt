package com.nandaadisaputra.noteapp.ui.auth

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.nandaadisaputra.noteapp.data.session.SharedPreferencesHelper
import com.nandaadisaputra.noteapp.databinding.ActivityLoginBinding
import com.nandaadisaputra.noteapp.utils.NavigationUtils
import com.nandaadisaputra.noteapp.utils.Resource
import com.nandaadisaputra.noteapp.utils.ToastUtils
import com.nandaadisaputra.noteapp.utils.disable
import com.nandaadisaputra.noteapp.utils.enable
import com.nandaadisaputra.noteapp.utils.hide
import com.nandaadisaputra.noteapp.utils.show

// Kelas LoginActivity adalah tampilan layar login
class LoginActivity : AppCompatActivity() {
    // View Binding untuk menghubungkan layout XML dengan kode Kotlin
    private lateinit var binding: ActivityLoginBinding

    // SharedPreferencesHelper digunakan untuk menyimpan dan mengambil token login
    private val sharedPreferencesHelper by lazy { SharedPreferencesHelper(this) }

    // Membuat instance dari AuthViewModel menggunakan ViewModelFactory
    private val authViewModel: AuthViewModel by viewModels {
        ViewModelFactory(sharedPreferencesHelper)
    }

    // Fungsi yang pertama kali dipanggil saat aktivitas dibuat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Menghubungkan layout dengan activity menggunakan View Binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkLoginStatus() // Cek apakah pengguna sudah login sebelumnya
        setupListeners() // Atur tombol login dan register
        observeLogin() // Pantau hasil login dari ViewModel
    }

    // Fungsi untuk mengecek apakah user sudah login dengan token yang tersimpan
    private fun checkLoginStatus() {
        if (!sharedPreferencesHelper.getToken().isNullOrEmpty()) {
            // Jika token ada, langsung arahkan ke halaman utama (home)
            NavigationUtils.navigateToHome(this)
        }
    }

    // Fungsi untuk mengatur event listener pada tombol login dan register
    private fun setupListeners() {
        // Event listener untuk tombol login
        binding.btnLogin.setOnClickListener {
            // Mengambil input email dan password dari EditText
            val email = binding.edtLoginEmail.text.toString().trim()
            val password = binding.edtLoginPassword.text.toString().trim()

            // Pola regex untuk validasi email
            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
            if (!email.matches(emailPattern.toRegex())) {
                // Tampilkan pesan error jika email tidak valid
                ToastUtils.showToast(this, "Format email tidak valid")
                return@setOnClickListener
            }

            // Jika email atau password kosong, tampilkan pesan error
            if (email.isEmpty() || password.isEmpty()) {
                ToastUtils.showToast(this, "Email dan Password wajib diisi")
            } else {
                // Jika input benar, kirim data login ke ViewModel
                authViewModel.login(email, password)
            }
        }

        // Event listener untuk teks register, navigasi ke halaman pendaftaran
        binding.tvRegister.setOnClickListener {
            NavigationUtils.navigateToActivity(this, RegisterActivity::class.java)
        }
    }

    // Fungsi untuk mengamati hasil login dari ViewModel
    private fun observeLogin() {
        authViewModel.loginResult.observe(this) { result ->
            when (result) {
                is Resource.Loading -> {
                    // Jika sedang proses login, tampilkan loading dan nonaktifkan tombol login
                    binding.progressBar.show()
                    binding.btnLogin.disable()
                }
                is Resource.Success -> {
                    // Jika login berhasil, sembunyikan loading dan aktifkan tombol login
                    binding.progressBar.hide()
                    binding.btnLogin.enable()
                    ToastUtils.showToast(this, "Login Berhasil")

                    // Simpan token login ke SharedPreferences dan navigasi ke halaman utama
                    result.data?.data?.token?.let { token ->
                        sharedPreferencesHelper.saveToken(token)
                        NavigationUtils.navigateToHome(this)
                    }
                }
                is Resource.Error -> {
                    // Jika terjadi error, sembunyikan loading dan tampilkan pesan error
                    binding.progressBar.hide()
                    binding.btnLogin.enable()
                    ToastUtils.showToast(this, result.message ?: "Login gagal")
                }
            }
        }
    }
}


