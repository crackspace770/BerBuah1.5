package com.bangkit.berbuah.ui.activities

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bangkit.berbuah.R
import com.bangkit.berbuah.api.ApiConfig
import com.bangkit.berbuah.databinding.ActivityLoginBinding
import com.bangkit.berbuah.model.UserModel
import com.bangkit.berbuah.model.UserPreference
import com.bangkit.berbuah.model.UserPreferences
import com.bangkit.berbuah.ui.fragments.HomeFragment
import com.bangkit.berbuah.response.LoginResponse
import com.bangkit.berbuah.viewmodel.AuthViewModelFactory
import com.bangkit.berbuah.viewmodel.LoginViewModel
import com.bangkit.berbuah.viewmodel.ViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class LoginActivity: AppCompatActivity(), View.OnClickListener {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var  binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var isRemembered = false
    private lateinit var user: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        isRemembered = sharedPreferences.getBoolean(IS_LOGIN, false)
        hasLogin(isRemembered)

        binding.loginButton.setOnClickListener(this)
        binding.signupButton.setOnClickListener(this)

        setupViewModel()
        playAnimation()
        setupView()
        setupAction()
    }

    private fun setupViewModel() {

        loginViewModel = ViewModelProvider(
            this,
            AuthViewModelFactory(UserPreferences.getInstance(dataStore))
        )[LoginViewModel::class.java]

        loginViewModel.getUser().observe(this) { user ->
            this.user = user
        }
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            when {
                email.isEmpty() -> {
                    binding.emailEditText.error = "Enter Your Email"
                }
                password.isEmpty() -> {
                    binding.passwordEditText.error = "Enter Your Password"
                }
                else -> {
                                    val mainIntent = Intent(this@LoginActivity, MainActivity::class.java)
                                    showLoading(true)
                                    startActivity(mainIntent)
                                    finish()
                                }
                            }




                }
            }


    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun hasLogin(boolean: Boolean) {
        if(boolean) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onClick(view: View) {
        when(view.id) {
            R.id.signupButton -> {
                val intent = Intent(this@LoginActivity, SignupActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun validateLogin(name: String, isLogin: Boolean){
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(NAME, name)
        editor.putBoolean(IS_LOGIN, isLogin)
        editor.apply()
    }


    private fun showLoading(isLoading: Boolean) {
        if(isLoading) binding.progressBar.visibility = View.VISIBLE
        else binding.progressBar.visibility = View.GONE
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

//        val title = ObjectAnimator.ofFloat(binding.tvLogin, View.ALPHA, 1f).setDuration(500)
        val title = ObjectAnimator.ofFloat(binding.tvTitle, View.ALPHA, 1f).setDuration(500)
        val emailTextView = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(500)
        val passwordTextView = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(
                title,
//                message,
                emailTextView,
                passwordTextView,
                login)
            startDelay = 500
        }.start()
    }

    companion object {

        val SHARED_PREFERENCES = "shared_preferences"
        val NAME = "name"
        val IS_LOGIN = "is_login"
    }

}