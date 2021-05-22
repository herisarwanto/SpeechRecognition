package com.englishlearningwithngram.ui.speechrecognition

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.englishlearningwithngram.model.WordsItem
import com.englishlearningwithngram.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class SpeechRecognitionViewModel(private val repository: Repository): ViewModel() {
    var apiResponse: MutableLiveData<Response<WordsItem>> = MutableLiveData()

    fun getWordPerLevel(level: Int){
        viewModelScope.launch {
            try {
                val response = repository.wordPerLevel(level)
                apiResponse.value = response
//                Log.e(this.javaClass.name, "getWord => ${response.body().toString()}")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}