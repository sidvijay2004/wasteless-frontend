package com.wasteless.activities

import android.content.Context
import android.content.Intent
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
import kotlinx.android.synthetic.main.activity_login.backButton

class LoginActivity : CustomAppActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        backButton.visibility = View.INVISIBLE
        setButtonActions()
    }

    private fun setButtonActions() {
        loginButton.setOnClickListener {
            validateCredentials()
        }
        signUpTV.setOnClickListener {
            val intent = Intent(applicationContext, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateCredentials() {
        val email = login_emailET.text.toString()
        val password = login_passwordET.text.toString()
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
                        Log.d("Login","success")
                        updateSharedPreferences(it)
                        val intent = Intent(applicationContext, HomeActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this,"Login error", Toast.LENGTH_SHORT).show()
                    }
                }
            })
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
        editor.putBoolean("loggedIn",true)
        editor.commit()

    }
}
