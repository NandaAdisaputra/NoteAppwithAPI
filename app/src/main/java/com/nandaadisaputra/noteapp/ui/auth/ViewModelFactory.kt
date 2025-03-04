package com.nandaadisaputra.noteapp.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nandaadisaputra.noteapp.data.repository.AuthRepository
import com.nandaadisaputra.noteapp.data.session.SharedPreferencesHelper
import com.nandaadisaputra.noteapp.ui.note.HomeViewModel

// ViewModelFactory untuk membuat berbagai ViewModel dengan dependensi yang diperlukan
class ViewModelFactory(
    private val sharedPreferencesHelper: SharedPreferencesHelper
) : ViewModelProvider.Factory {

    // Inisialisasi repository sekali saja
    private val repository = AuthRepository(sharedPreferencesHelper)

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when (modelClass) {
            AuthViewModel::class.java -> AuthViewModel(repository, sharedPreferencesHelper)
            HomeViewModel::class.java -> HomeViewModel(repository)
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        } as T
}
