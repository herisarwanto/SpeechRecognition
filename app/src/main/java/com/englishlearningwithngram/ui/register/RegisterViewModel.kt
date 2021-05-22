package com.englishlearningwithngram.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.englishlearningwithngram.model.RegisterResponse
import com.englishlearningwithngram.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class RegisterViewModel(private val repository: Repository) : ViewModel() {

    var registerResponse: MutableLiveData<Response<RegisterResponse>> = MutableLiveData()

    fun register(nama: String, email: String, username: String, password: String) {
        viewModelScope.launch {
            val response = repository.register(nama, email, username, password)
            registerResponse.value = response
        }
    }
}