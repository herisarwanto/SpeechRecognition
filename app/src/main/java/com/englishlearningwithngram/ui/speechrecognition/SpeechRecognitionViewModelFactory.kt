package com.englishlearningwithngram.ui.speechrecognition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.englishlearningwithngram.repository.Repository

class SpeechRecognitionViewModelFactory(private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SpeechRecognitionViewModel(repository) as T
    }
}