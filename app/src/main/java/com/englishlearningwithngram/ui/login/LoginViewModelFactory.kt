package com.englishlearningwithngram.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.englishlearningwithngram.repository.Repository
import com.englishlearningwithngram.ui.register.RegisterViewModel

class LoginViewModelFactory(private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(repository) as T
    }
}