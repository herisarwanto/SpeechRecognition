package com.englishlearningwithngram.model


import com.google.gson.annotations.SerializedName

data class WordsItem(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("status")
    val status: Int,
    @SerializedName("word")
    val word: List<Word>
)