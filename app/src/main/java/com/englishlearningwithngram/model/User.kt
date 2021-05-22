package com.englishlearningwithngram.model
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id_user")
    val id_user: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("nama")
    val nama: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("dibuat_tanggal")
    val dibuat_tanggal: String
)