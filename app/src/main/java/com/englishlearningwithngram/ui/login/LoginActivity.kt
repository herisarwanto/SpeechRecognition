package com.englishlearningwithngram.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.englishlearningwithngram.R
import com.englishlearningwithngram.databinding.ActivityLoginBinding
import com.englishlearningwithngram.repository.Repository
import com.englishlearningwithngram.ui.HomeActivity
import com.englishlearningwithngram.ui.register.RegisterActivity
import com.englishlearningwithngram.utils.SharedPrefManager

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var activityLoginBinding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    private var email: String? = null
    private var password: String? = null
    private val minLength = 6
    private var sharedPrefManager: SharedPrefManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(activityLoginBinding.root)
        sharedPrefManager = SharedPrefManager(this)

        val repository = Repository()
        val viewModelFactory = LoginViewModelFactory(repository)
        loginViewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)

        if(sharedPrefManager!!.isLoggedIn()) {
            openHomeActivity()
        }

        activityLoginBinding.btnLogin.setOnClickListener(this)
        activityLoginBinding.btnRegister.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_register -> {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_login -> {
                email = activityLoginBinding.tilUsername.text.toString().trim()
                password = activityLoginBinding.tilPassword.text.toString().trim()

//                email = "user@gmail.com"
//                password = "123456"
                when {
                    TextUtils.isEmpty(email) -> {
                        activityLoginBinding.tilUsername.error = showErrorMessage()
                    }
                    TextUtils.isEmpty(password) -> {
                        activityLoginBinding.tilPassword.error = showErrorMessage()
                    }
                    password!!.length < minLength -> {
                        activityLoginBinding.tilPassword.error = getString(R.string.min_password)
                    }
                    else -> {
                        doLogin()
                    }
                }
            }
        }
    }

    private fun doLogin() {
        try {
            activityLoginBinding.cardview.cardElevation = 0F
            activityLoginBinding.llProgressbar.visibility = View.VISIBLE
//            Log.e(this.javaClass.name, "doLogin email, password => $email, $password")
            loginViewModel.login(email!!, password!!)
            loginViewModel.loginResponse.observe(this, { response ->
                if(response.isSuccessful) {
                    Log.e(this.javaClass.name, "doLogin isSuccessful")
                    if (response.body()?.error==false) {
                        Log.e(this.javaClass.name, "doLogin If success")
                        sharedPrefManager!!.saveStatusLogin(true)
                        sharedPrefManager!!.saveString(
                            response.body()!!.user.id_user,
                            response.body()!!.user.email,
                            response.body()!!.user.nama,
                            response.body()!!.user.username,
                            response.body()!!.user.dibuat_tanggal
                        )
//                        Toast.makeText(this, getString(R.string.login_success), Toast.LENGTH_SHORT)
//                            .show()
                        openHomeActivity()
                    } else {
                        Log.e(this.javaClass.name, "doLogin Else failed")

                        activityLoginBinding.cardview.cardElevation = 2F
                        activityLoginBinding.tvError.visibility = View.VISIBLE
                    }

                    activityLoginBinding.llProgressbar.visibility = View.GONE

                } else {
                    Log.e(this.javaClass.name, "doLogin notSuccessful")

                    activityLoginBinding.llProgressbar.visibility = View.GONE
                    Toast.makeText(this, getString(R.string.login_fail), Toast.LENGTH_LONG).show()
                }
            })
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            activityLoginBinding.llProgressbar.visibility = View.GONE
            e.printStackTrace()
        }
    }

    private fun openHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showErrorMessage() : String {
        return getString(R.string.empty_field)
    }
}
















