package com.wasteless.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.wasteless.R
import com.wasteless.model.LoginCredential
import com.wasteless.model.Participant
import com.wasteless.utils.Utilities
import com.wasteless.viewmodels.ParticipantViewModel
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : CustomAppActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        backButton.visibility = View.INVISIBLE
        setButtonActions()
        val preferences: SharedPreferences = this.getSharedPreferences("com.wasteless", 0)
        val isLoggedIn = preferences.getBoolean("isLoggedIn",false)
        if (isLoggedIn) {
            val email = preferences.getString("email", "")
            val password = preferences.getString("password", "")
            validateCredentials(email!!, password!!)
        }
    }

    private fun setButtonActions() {
        forgotPasswordTV.setOnClickListener {
            val intent = Intent(applicationContext, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
        loginButton.setOnClickListener {

            val email = login_emailET.text.toString()
            val password = login_passwordET.text.toString()
            validateCredentials(email,password)
        }
        signUpTV.setOnClickListener {
            val intent = Intent(applicationContext, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateCredentials(email: String, password: String) {
        if(Utilities().isInternetAvailable()){
            loginProgress.visibility = View.VISIBLE
            val credentials = LoginCredential(email,password)
            var participantViewModel = ViewModelProviders.of(this).get(ParticipantViewModel::class.java)
            participantViewModel.init()
            participantViewModel.validateCredentials(credentials)
            participantViewModel.getParticipantData().observe(this, Observer {
                it?.let {
                    loginProgress.visibility = View.GONE
                    if(it.id != null) {
                        errorTV.visibility = View.INVISIBLE
                        Log.d("Login","success")
                        updateSharedPreferences(it)
                        val intent = Intent(applicationContext, HomeActivity::class.java)
                        startActivity(intent)
                    } else {
                        errorTV.visibility = View.VISIBLE
                        errorTV.text = "Email/Password is incorrect"
                        Toast.makeText(this,"Login error", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        } else {
            errorTV.visibility = View.VISIBLE
            errorTV.text = "No Internet Connection. Try again!"
        }
    }

    private fun updateSharedPreferences(participant: Participant) {
        val sharedPreference =  this.getSharedPreferences(getString(R.string.pref_name), Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putString("id",participant.id!!.toString())
        editor.putString("name",participant.name)
        editor.putString("email",participant.email)
        editor.putString("password",participant.password)
        editor.putString("address1",participant.address1)
        editor.putString("address2",participant.address2)
        editor.putString("state",participant.state)
        editor.putString("city",participant.city)
        editor.putString("zipcode",participant.zipcode)
        editor.putString("country",participant.country)
        editor.putString("phone",participant.phone)
        editor.putBoolean("isLoggedIn",true)
        editor.commit()

    }
}
