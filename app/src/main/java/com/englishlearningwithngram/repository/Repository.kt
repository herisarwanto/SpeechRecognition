package com.englishlearningwithngram.repository

import com.englishlearningwithngram.model.RegisterResponse
import com.englishlearningwithngram.model.UserItem
import com.englishlearningwithngram.model.WordsItem
import com.englishlearningwithngram.model.api.RetrofitInstance
import retrofit2.Response

class Repository {
    suspend fun register(
        nama: String,
        email: String,
        username: String,
        password: String
    ): Response<RegisterResponse> {
        return RetrofitInstance.api.registerUser(nama, email, username, password)
    }

    suspend fun login(
        email: String,
        password: String
    ): Response<UserItem> {
        return RetrofitInstance.api.loginUser(email, password)
    }

    suspend fun wordPerLevel(
        level: Int
    ): Response<WordsItem> {
        return RetrofitInstance.api.getWordsPerLevel(level)
    }
}