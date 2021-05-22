package com.englishlearningwithngram.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.englishlearningwithngram.model.User
import com.englishlearningwithngram.model.UserItem
import com.englishlearningwithngram.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(private val repository: Repository): ViewModel() {
    var loginResponse: MutableLiveData<Response<UserItem>> = MutableLiveData()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            Log.e(this.javaClass.name, "login => Start")
            try {
                val response = repository.login(email, password)
                Log.e(this.javaClass.name, "login => ${response.body().toString()}")
                loginResponse.value = response
            } catch (e: Exception) {
                Log.e(this.javaClass.name, "login CATCH => ${e.message}")
            }
        }
    }
}