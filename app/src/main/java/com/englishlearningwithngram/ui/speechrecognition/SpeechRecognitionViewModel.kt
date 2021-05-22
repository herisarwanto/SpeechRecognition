package com.englishlearningwithngram.ui.speechrecognition

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.englishlearningwithngram.model.WordModel
import com.englishlearningwithngram.repository.Repository
import org.json.JSONArray
import java.io.IOException
import java.io.InputStream

class SpeechRecognitionViewModel(private val repository: Repository) : ViewModel() {
    //    var apiResponse: MutableLiveData<Response<WordsItem>> = MutableLiveData()
    var apiResponseJsonAssets: MutableLiveData<List<WordModel>> = MutableLiveData()
    private val listWord: MutableList<WordModel> = mutableListOf()

//    fun getWordPerLevel(level: Int){
//        viewModelScope.launch {
//            try {
//                val response = repository.wordPerLevel(level)
//                apiResponse.value = response
//
//                Log.e("getWordPerLevel", "kata => $response")
//
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }

    fun getWord(level: Int, inputStream: InputStream) {
        val json: String?

        try {
            json = inputStream.bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(json)

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)

                if (jsonObject.getString("level") == level.toString()) {
                    val wordModel = WordModel(
                        jsonObject.getString("id_kata"),
                        jsonObject.getString("kata"),
                        jsonObject.getString("level")
                    )

                    listWord.add(wordModel)

                } else {
                    Log.e("getWord", "list word => $listWord")
                }

                Log.e("getWord", "list word => $listWord")

            }

            apiResponseJsonAssets.value = listWord

        } catch (e: IOException) {

        }
    }

}