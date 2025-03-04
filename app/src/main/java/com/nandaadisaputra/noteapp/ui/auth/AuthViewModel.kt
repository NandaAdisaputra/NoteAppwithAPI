package com.nandaadisaputra.noteapp.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nandaadisaputra.noteapp.data.model.AuthResponse
import com.nandaadisaputra.noteapp.data.repository.AuthRepository
import com.nandaadisaputra.noteapp.data.session.SharedPreferencesHelper
import com.nandaadisaputra.noteapp.utils.Resource
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * ViewModel untuk menangani proses autentikasi (Register & Login)
 *
 * @param repository Instance dari AuthRepository untuk melakukan request ke API.
 * @param sharedPreferencesHelper Untuk menyimpan token autentikasi setelah login berhasil.
 */
class AuthViewModel(
    private val repository: AuthRepository,
    private val sharedPreferencesHelper: SharedPreferencesHelper
) : ViewModel() {

    // MutableLiveData untuk hasil register
    private val _registerResponse = MutableLiveData<Resource<AuthResponse>>()
    val registerResponse: LiveData<Resource<AuthResponse>> get() = _registerResponse

    // MutableLiveData untuk hasil login
    private val _loginResult = MutableLiveData<Resource<AuthResponse>>()
    val loginResult: LiveData<Resource<AuthResponse>> get() = _loginResult

    /**
     * Fungsi untuk melakukan registrasi
     * @param username Nama pengguna
     * @param email Alamat email
     * @param password Kata sandi
     */
    fun register(username: String, email: String, password: String) {
        // Konversi data ke RequestBody karena API menggunakan Multipart
        val usernameBody = username.toRequestBody("text/plain".toMediaTypeOrNull())
        val emailBody = email.toRequestBody("text/plain".toMediaTypeOrNull())
        val passwordBody = password.toRequestBody("text/plain".toMediaTypeOrNull())

        // Set status Loading sebelum memulai request
        _registerResponse.postValue(Resource.Loading())

        // Menjalankan proses register dalam coroutine
        viewModelScope.launch {
            val response = repository.register(usernameBody, emailBody, passwordBody)
            _registerResponse.postValue(response) // Update hasil ke LiveData
        }
    }

    /**
     * Fungsi untuk melakukan login
     * @param email Alamat email
     * @param password Kata sandi
     */
    fun login(email: String, password: String) {
        // Konversi data ke RequestBody
        val emailBody = email.toRequestBody("text/plain".toMediaTypeOrNull())
        val passwordBody = password.toRequestBody("text/plain".toMediaTypeOrNull())

        // Set status Loading sebelum memulai request
        _loginResult.postValue(Resource.Loading())

        // Menjalankan proses login dalam coroutine
        viewModelScope.launch {
            val response = repository.login(emailBody, passwordBody)

            // Jika login sukses, simpan token ke SharedPreferences
            if (response is Resource.Success) {
                response.data?.data?.token?.let { token ->
                    sharedPreferencesHelper.saveToken(token)
                }
            }

            // Update hasil ke LiveData
            _loginResult.postValue(response)
        }
    }
}
