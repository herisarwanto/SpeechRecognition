package com.englishlearningwithngram.ui.speechrecognition

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.englishlearningwithngram.R
import com.englishlearningwithngram.databinding.ActivitySpeechRecognitionBinding
import com.englishlearningwithngram.model.WordModel
import com.englishlearningwithngram.repository.Repository
import java.io.InputStream
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random.Default.nextInt

const val KEY_LEVEL = "level"

class SpeechRecognitionActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySpeechRecognitionBinding
    private lateinit var viewModel: SpeechRecognitionViewModel

    private val RECOGNIZER_RESULT = 1
    private var speech: String = ""
    private var speechResult: String? = null
    private var progr = 0
    private var level: Int? = null

    //    private var listWord: List<Word>? = null
    private var randomValue: Int = 0
    private var inputStream: InputStream? = null

    var arrayWord: List<WordModel>? = null
    var listWord: MutableList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpeechRecognitionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = Repository()
        val viewModelFactory = SpeechRecognitionViewModelFactory(repository)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(SpeechRecognitionViewModel::class.java)

        binding.ivMic.setOnClickListener(this)
        binding.ibBack.setOnClickListener(this)
//        binding.ibRefresh.setOnClickListener(this)

        inputStream = assets.open("easy_level.json")

        initComponent()

        val arrayAdapter = ArrayAdapter(
            this@SpeechRecognitionActivity, android.R.layout.simple_list_item_1, listWord
        )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.inputWord.setAdapter(arrayAdapter)

        binding.inputWord.onItemClickListener =
            AdapterView.OnItemClickListener { parent, arg1, position, id ->
                Log.e("SELECTED => ", listWord[position])
                speech = listWord[position].toString()
            }
    }

    private fun initComponent() {
        val bundle = intent.extras
        if (bundle != null) {
            level = bundle.getInt(KEY_LEVEL)
        }

//        getWordPerLevel()
        getWord()
    }

    //get data from server repository
//    private fun getWordPerLevel() {
//        viewModel.getWord(level!!, inputStream!!)
//        viewModel.getWordPerLevel(level!!)
//        viewModel.apiResponse.observe(this, { response ->
//            if (response.isSuccessful) {
//                listWord = response.body()?.word
//
//                Log.e(this.javaClass.name, "getWord isSuccessful => $listWord")
//
//                //random kata
//                randomValue = nextInt(listWord!!.size)
//                speech = listWord!![randomValue].kata
//                binding.tvSpeech.text = speech
//
//            } else {
//                Log.e(this.javaClass.name, "getWord notSuccessful => ${response.body().toString()}")
//            }
//        })
//    }

    private fun getWord() {
        viewModel.getWord(level!!, inputStream!!)
        viewModel.apiResponseJsonAssets.observe(this, { response ->

            for (i in response.indices) {
                listWord.add(response[i].kata)
            }

//            arrayWord = response
//            randomValue = nextInt(response.size)
//            speech = arrayWord!![randomValue].kata

//            binding.tvSpeech.text = speech
        })
    }

    private fun updateProgressBar() {
        binding.progressBar.progress = progr
        binding.textViewProgress.text = "$progr%"
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ib_back -> {
                finish()
            }
//            R.id.ib_refresh -> {
//                randomValue = nextInt(arrayWord!!.size)
//                speech = arrayWord!![randomValue].kata
//                binding.tvSpeech.text = speech
//                progr = 0
//                binding.tvSpeechResult.text = "..."
//                updateProgressBar()
//            }
            R.id.iv_mic -> {
                binding.tvSpeechResult.text = "..."
                progr = 0
                updateProgressBar()

                val speechIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                speechIntent.putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
                speechIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech To Text")
                startActivityForResult(speechIntent, RECOGNIZER_RESULT)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RECOGNIZER_RESULT && resultCode == RESULT_OK) {
            val matches: ArrayList<String>? =
                data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            speechResult = matches?.get(0).toString()

            Log.e(this.javaClass.name, "onActivityResult, matches => ${matches.toString()}")

            textTest()
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    @SuppressLint("DefaultLocale")
    private fun textTest() {
        try {
            val speechCompare = speechResult
            val first: CharArray = speech.lowercase().toCharArray()
            val second: CharArray = speechResult?.lowercase()!!.toCharArray()

            var counter = 0

            val minLength = min(first.size, second.size)
            val maxLength = max(first.size, second.size)

            Log.e("textTest", "Speech 1 => $speech")
            Log.e("textTest", "Speech 2 => $speechResult")
            Log.e("textTest", "minLength => $minLength")
            Log.e("textTest", "maxLength => $maxLength")

//            val wordDb: ArrayList<String> = ArrayList()
//            val wordSpeech: ArrayList<String> = ArrayList()

//            for (i in first.indices) {
//                if (i == first.lastIndex) {
//                    wordDb.add("${first[i]}#")
//                } else {
//                    wordDb.add("${first[i]}${first[i + 1]}")
//                }
//            }
//
//            for (i in second.indices) {
//                if (i == second.lastIndex) {
//                    wordSpeech.add("${second[i]}#")
//                } else {
//                    wordSpeech.add("${second[i]}${second[i + 1]}")
//                }
//            }
//
//            val maxLengthWord = max(wordDb.size, wordSpeech.size)
//            val minLengthWord = min(wordDb.size, wordSpeech.size)
//
//            for (i in 0 until minLengthWord) {
//                if (wordDb[i] != wordSpeech[i]) {
//
//                } else {
//                    counter++
//                }
//            }

//            Log.e("textTest", "Word DB => $wordDb")
//            Log.e("textTest", "Word Speech => $wordSpeech")
//
//            binding.tvA.text = "Kata 1 : $wordDb"
//            binding.tvB.text = "Kata 2 : $wordSpeech"

            for (i in 0 until minLength) {
                if (first[i] != second[i]) {
                    //TODO: This
                } else {
                    counter++
                }
            }

            //Untuk mencari persentase kecocokan kata
            val doubleCounter: Double = counter.toDouble()
            val doubleMaxLength: Double = maxLength.toDouble()

            val result: Double = doubleCounter / doubleMaxLength * 100

//            binding.tvMath.text =
//                "=> ${doubleCounter.toInt()}/${doubleMaxLength.toInt()} x 100 = ${result.toInt()}%"
            Log.e(this.javaClass.name, "textTest End, Progress Percent => $progr")
            progr = result.toInt()
            updateProgressBar()

            Log.e("textTest", "Result => $result")

            binding.tvSpeechResult.text = speechCompare

        } catch (e: Exception) {
            Log.e(this.javaClass.name, "textTest CATCH => ${e.message}")
        }
    }

}















