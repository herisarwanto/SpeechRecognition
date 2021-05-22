package com.englishlearningwithngram.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.englishlearningwithngram.R
import com.englishlearningwithngram.databinding.ActivityHomeBinding
import com.englishlearningwithngram.ui.login.LoginActivity
import com.englishlearningwithngram.ui.speechrecognition.KEY_LEVEL
import com.englishlearningwithngram.ui.speechrecognition.SpeechRecognitionActivity
import com.englishlearningwithngram.utils.SharedPrefManager

class HomeActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityHomeBinding
    private var sharedPrefManager: SharedPrefManager? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPrefManager = SharedPrefManager(this)

        val nama = sharedPrefManager!!.getValueString("nama")

        binding.tvHello.text = "Hello, $nama"

        binding.btnEasy.setOnClickListener(this)
        binding.btnMedium.setOnClickListener(this)
        binding.btnHard.setOnClickListener(this)
        binding.btnMedium.setOnClickListener(this)
        binding.btnLogout.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.btn_easy -> {
                openActivity(1)
            }
            R.id.btn_medium -> {
                openActivity(2)
            }
            R.id.btn_hard -> {
                openActivity(3)
            }
            R.id.btn_logout -> {
                alertDialog()
            }
        }
    }

    private fun alertDialog() {
        // Initialize a new instance of
        val builder = AlertDialog.Builder(this@HomeActivity)

        builder.setTitle(getString(R.string.app_name))

        // Display a message on alert dialog
        builder.setMessage("Logout Akun?")

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton("YES"){ _, _ ->
            // Do something when user press the positive button
            sharedPrefManager!!.logout()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Display a negative button on alert dialog
        builder.setNegativeButton("No"){ _, _ ->
//            Toast.makeText(applicationContext,"You are not agree.",Toast.LENGTH_SHORT).show()
        }

        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()
    }

    private fun openActivity(level: Int) {
        val intent = Intent(this, SpeechRecognitionActivity::class.java)
        intent.putExtra(KEY_LEVEL, level)
        startActivity(intent)
    }

}