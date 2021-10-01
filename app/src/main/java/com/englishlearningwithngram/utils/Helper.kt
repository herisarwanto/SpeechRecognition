package com.englishlearningwithngram.utils

class Helper {

    //With Bigram
//    @SuppressLint("DefaultLocale")
//    private fun bigram() {
//        try {
//            val speechCompare = speechResult
//            val first: CharArray = speech.lowercase().toCharArray()
//            val second: CharArray = speechResult?.lowercase()!!.toCharArray()
//
//            var counter = 0
//
//            val minLength = min(first.size, second.size)
//            val maxLength = max(first.size, second.size)
//
//            Log.e("textTest", "Speech 1 => $speech")
//            Log.e("textTest", "Speech 2 => $speechResult")
//            Log.e("textTest", "minLength => $minLength")
//            Log.e("textTest", "maxLength => $maxLength")
//
//            val wordDb: ArrayList<String> = ArrayList()
//            val wordSpeech: ArrayList<String> = ArrayList()
//
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
//
//            Log.e("textTest", "Word DB => $wordDb")
//            Log.e("textTest", "Word Speech => $wordSpeech")
//
//            binding.tvA.text = "Kata 1 : $wordDb"
//            binding.tvB.text = "Kata 2 : $wordSpeech"
//
////            for (i in 0 until minLength) {
////                if (first[i] != second[i]) {
////                    //TODO: This
////                } else {
////                    counter++
////                }
////            }
//
//            //Untuk mencari persentase kecocokan kata
//            val doubleCounter: Double = counter.toDouble()
//            val doubleMaxLength: Double = maxLength.toDouble()
//
//            val result: Double = doubleCounter / doubleMaxLength * 100
//
//            binding.tvMath.text =
//                "=> ${doubleCounter.toInt()}/${doubleMaxLength.toInt()} x 100 = ${result.toInt()}%"
//            Log.e(this.javaClass.name, "textTest End, Progress Percent => $progr")
//            progr = result.toInt()
//            updateProgressBar()
//
//            Log.e("textTest", "Result => $result")
//
//            binding.tvSpeechResult.text = speechCompare
//
//        } catch (e: Exception) {
//            Log.e(this.javaClass.name, "textTest CATCH => ${e.message}")
//        }
//    }

}