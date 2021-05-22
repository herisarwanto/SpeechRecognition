package com.englishlearningwithngram.ui.register

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.englishlearningwithngram.R
import com.englishlearningwithngram.databinding.ActivityRegisterBinding
import com.englishlearningwithngram.repository.Repository
import com.englishlearningwithngram.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private val TAG = this.javaClass.name
    private val minLength = 6
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    private lateinit var activityRegisterBinding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    private var nama: String? = null
    private var email: String? = null
    private var username: String? = null
    private var password: String? = null
    private val toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(activityRegisterBinding.root)

        val repository = Repository()
        val viewModelFactory = RegisterViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(RegisterViewModel::class.java)

        activityRegisterBinding.ibBack.setOnClickListener(this)
        activityRegisterBinding.btnRegister.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        nama = activityRegisterBinding.etNama.text.toString().trim()
        email = activityRegisterBinding.etEmail.text.toString().trim()
        username = activityRegisterBinding.etUsername.text.toString().trim()
        password = activityRegisterBinding.etPassword.text.toString().trim()

        when(v.id) {
            R.id.ib_back -> {
                finish()
            }
            R.id.btn_register -> {
                when {
                    TextUtils.isEmpty(nama) -> {
                        activityRegisterBinding.etNama.error = showErrorMessage(1)
                    }
                    TextUtils.isEmpty(email) -> {
                        activityRegisterBinding.etEmail.error = showErrorMessage(1)
                    }
                    TextUtils.isEmpty(username) -> {
                        activityRegisterBinding.etUsername.error = showErrorMessage(1)
                    }
                    TextUtils.isEmpty(password) -> {
                        activityRegisterBinding.etPassword.error = showErrorMessage(1)
                    }
                    password!!.length < minLength -> {
                        activityRegisterBinding.etPassword.error = showErrorMessage(2)
                    }
                    !email!!.matches(emailPattern.toRegex()) -> {
                        activityRegisterBinding.etEmail.error = showErrorMessage(3)
                    }
                    else -> {
                        doRegister()
                    }
                }
            }
        }
    }

    private fun doRegister() {
        try {
//            activityRegisterBinding.cardview.cardElevation = 0F
            activityRegisterBinding.llProgressbar.visibility = View.VISIBLE
            viewModel.register(nama!!, email!!, username!!, password!!)
            viewModel.registerResponse.observe(this, { response ->
                if (response.isSuccessful) {
                    Log.e(TAG, "response body => ${response.body().toString()}")
                    Toast.makeText(this, response.body()?.message, Toast.LENGTH_LONG).show()
                    openLoginActivity()
                    activityRegisterBinding.llProgressbar.visibility = View.GONE
                } else {
                    Toast.makeText(this, response.body()?.message, Toast.LENGTH_LONG).show()
                }
            })
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            activityRegisterBinding.llProgressbar.visibility = View.GONE
        }
    }

    private fun showErrorMessage(code: Int) : String {
        var message = ""
        when (code) {
            1 -> {
                message = getString(R.string.empty_field)
            }
            2 -> {
                message = getString(R.string.min_password)
            }
            3 -> {
                message = getString(R.string.email_not_valid)
            }
        }
        return message
    }

//    private fun initToolbar() {
//        activityRegisterBinding.apply {
//            toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
//            setSupportActionBar(toolbar)
//            toolbar.setTitleTextColor(Color.WHITE)
//            toolbar.setNavigationOnClickListener { finish() }
//        }
//    }

    private fun openLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}



















