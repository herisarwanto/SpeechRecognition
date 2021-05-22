package com.englishlearningwithngram.ui.speechrecognition

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.englishlearningwithngram.R
import com.englishlearningwithngram.databinding.ActivitySpeechRecognitionBinding
import com.englishlearningwithngram.model.Word
import com.englishlearningwithngram.repository.Repository
import java.util.*
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
    private var listWord: List<Word>? = null
    private var randomValue: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpeechRecognitionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = Repository()
        val viewModelFactory = SpeechRecognitionViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SpeechRecognitionViewModel::class.java)

        binding.ivMic.setOnClickListener(this)
        binding.ibBack.setOnClickListener(this)
        binding.ibRefresh.setOnClickListener(this)

        initComponent()
//        textTest()
    }

    private fun initComponent() {
        val bundle = intent.extras
        if (bundle != null) {
            level = bundle.getInt(KEY_LEVEL)
        }

        getWordPerLevel()
    }

    private fun getWordPerLevel() {
        viewModel.getWordPerLevel(level!!)
        viewModel.apiResponse.observe(this, { response ->
            if (response.isSuccessful) {
//                Log.e(this.javaClass.name, "getWord isSuccessful => ${response.body().toString()}")
                listWord = response.body()?.word
                //random kata
                randomValue = nextInt(listWord!!.size)
                speech = listWord!![randomValue].kata
                binding.tvSpeech.text = speech

            } else {
                Log.e(this.javaClass.name, "getWord notSuccessful => ${response.body().toString()}")
            }
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
            R.id.ib_refresh -> {
                randomValue = nextInt(listWord!!.size)
                speech = listWord!![randomValue].kata
                binding.tvSpeech.text = speech
                progr = 0
                binding.tvSpeechResult.text = "..."
                updateProgressBar()
            }
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
            var speechCompare = speechResult

//            speech = "Learning"
//            speechResult = "learning"

            val first: CharArray = speech.toLowerCase().toCharArray()
            val second: CharArray = speechResult?.toLowerCase()!!.toCharArray()
            var counter = 0

            val minLength = min(first.size, second.size)
            val maxLength = max(first.size, second.size)

//        when {
//            first.size == second.size -> {
            Log.e(this.javaClass.name, "textTest IF, first => ${first.size}")
            Log.e(this.javaClass.name, "textTest IF, second => ${second.size}")
            Log.e(this.javaClass.name, "textTest IF, minLength => $minLength")

            for (i in 0 until minLength) {
                Log.e(this.javaClass.name, "textTest IF, speech => ${first[i]}")
                Log.e(this.javaClass.name, "textTest IF, result => ${second[i]}")

                if (first[i] != second[i]) {

//                    if (counter > 0) counter-- else counter = 0

                    Log.e(this.javaClass.name, "textTest IF !=, counter => $counter")
                } else {
                    counter++

                    Log.e(this.javaClass.name, "textTest ELSE, counter => $counter")
                }
            }

            Log.e(this.javaClass.name, "textTest End, counter => $counter")
            Log.e(this.javaClass.name, "textTest End, maxLength => $maxLength")

            //Untuk mencari persentase kecocokan kata
            var doubleCounter: Double = counter.toDouble()
            var doubleMaxLength: Double = maxLength.toDouble()

            var result: Double = doubleCounter / doubleMaxLength * 100
            var resultAdd: Double = (counter + maxLength).toDouble()
            var resultMin: Double = (counter - maxLength).toDouble()

            Log.e(this.javaClass.name, "textTest Result, result => $result")
            Log.e(this.javaClass.name, "textTest Result, resultAdd => $resultAdd")
            Log.e(this.javaClass.name, "textTest Result, resultMin => $resultMin")

            Log.e(this.javaClass.name, "textTest End, Progress Percent => $progr")

            progr = result.toInt()
            updateProgressBar()
            binding.tvSpeechResult.text = speechCompare

//            }
//            first.size < second.size -> {
////                Toast.makeText(this, "Jumlah kata melebihi", Toast.LENGTH_SHORT).show()
//                speechCompare = "Jumlah kata melebihi"
//            }
//            first.size > second.size -> {
////                Toast.makeText(this, "Jumlah kata kurang", Toast.LENGTH_SHORT).show()
//                speechCompare = "Jumlah kata kurang"
//            }
//        }

//            Log.e(this.javaClass.name, "textTest End counter => $counter")
//            Log.e(this.javaClass.name, "textTest maxLength => $maxLength")
//            Log.e(this.javaClass.name, "textTest progr => $progr")
//
//            if (counter> 0) {
//                progr = (maxLength / counter) * 100
//
//                var test = maxLength / counter
//
//                Log.e(this.javaClass.name, "textTest IF test => $test")
//                Log.e(this.javaClass.name, "textTest IF progr => $progr")
//            }
//            updateProgressBar()
//            binding.tvSpeechResult.text = speechCompare
//        } else {
//            binding.tvSpeechResult.text = "Kata yang diucapkan tidak cocok"
//        }
        } catch (e: Exception) {
            Log.e(this.javaClass.name, "textTest CATCH => ${e.message}")
        }

    }

}















