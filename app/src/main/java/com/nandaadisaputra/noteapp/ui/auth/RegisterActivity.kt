package com.nandaadisaputra.noteapp.ui.auth


import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.nandaadisaputra.noteapp.data.session.SharedPreferencesHelper
import com.nandaadisaputra.noteapp.databinding.ActivityRegisterBinding
import com.nandaadisaputra.noteapp.utils.NavigationUtils
import com.nandaadisaputra.noteapp.utils.Resource
import com.nandaadisaputra.noteapp.utils.ToastUtils
import com.nandaadisaputra.noteapp.utils.disable
import com.nandaadisaputra.noteapp.utils.enable
import com.nandaadisaputra.noteapp.utils.hide
import com.nandaadisaputra.noteapp.utils.show

// Kelas RegisterActivity untuk menangani registrasi pengguna
class RegisterActivity : AppCompatActivity() {
    // View Binding untuk menghubungkan layout XML dengan kode Kotlin
    private lateinit var binding: ActivityRegisterBinding

    // SharedPreferencesHelper untuk menyimpan data pengguna
    private val sharedPreferencesHelper by lazy { SharedPreferencesHelper(this) }

    // Membuat instance dari AuthViewModel menggunakan ViewModelFactory
    private val authViewModel: AuthViewModel by viewModels {
        ViewModelFactory(sharedPreferencesHelper)
    }

    // Fungsi yang pertama kali dipanggil saat aktivitas dibuat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Menghubungkan layout dengan activity menggunakan View Binding
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Observasi hasil registrasi dari ViewModel
        authViewModel.registerResponse.observe(this, Observer { resource ->
            when (resource) {
                is Resource.Loading -> {
                    // Jika sedang proses registrasi, tampilkan loading dan nonaktifkan tombol register
                    binding.progressBar.show()
                    binding.btnRegister.disable()
                }
                is Resource.Success -> {
                    // Jika registrasi berhasil, sembunyikan loading dan aktifkan tombol register
                    binding.progressBar.hide()
                    binding.btnRegister.enable()

                    // Tampilkan pesan sukses
                    ToastUtils.showToast(this, "Registrasi berhasil! Selamat datang, ${resource.data?.message}")

                    // Navigasi ke halaman login setelah registrasi berhasil
                    NavigationUtils.navigateToLogin(this)
                }
                is Resource.Error -> {
                    // Jika terjadi error, sembunyikan loading dan tampilkan pesan error
                    binding.progressBar.hide()
                    binding.btnRegister.enable()
                    ToastUtils.showToast(this, resource.message ?: "Kesalahan tidak diketahui")
                }
            }
        })

        // Event listener untuk tombol register
        binding.btnRegister.setOnClickListener {
            // Mengambil input dari EditText
            val username = binding.edtUsername.text.toString().trim()
            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()

            // Pola regex untuk validasi email
            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
            if (!email.matches(emailPattern.toRegex())) {
                // Tampilkan pesan error jika email tidak valid
                ToastUtils.showToast(this, "Format email tidak valid")
                return@setOnClickListener
            }

            // Validasi input kosong
            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                ToastUtils.showToast(this, "Harap isi semua kolom sebelum melanjutkan.")
            }
            // Validasi panjang password
            else if (password.length < 8) {
                ToastUtils.showToast(this, "Kata sandi harus memiliki minimal 8 karakter.")
            }
            // Jika semua validasi terpenuhi, lakukan registrasi
            else {
                authViewModel.register(username, email, password)
            }
        }
    }
}
