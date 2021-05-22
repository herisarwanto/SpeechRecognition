package com.englishlearningwithngram.model


import com.google.gson.annotations.SerializedName

data class UserItem(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("status")
    val status: Int,
    @SerializedName("user")
    val user: User
)