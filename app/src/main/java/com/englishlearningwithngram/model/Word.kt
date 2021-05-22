package com.englishlearningwithngram.model


import com.google.gson.annotations.SerializedName

data class Word(
    @SerializedName("id_kata")
    val id_kata: String,
    @SerializedName("kata")
    val kata: String,
    @SerializedName("level")
    val level: String
)