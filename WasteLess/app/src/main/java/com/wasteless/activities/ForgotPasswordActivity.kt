package com.wasteless.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.wasteless.R
import com.wasteless.viewmodels.ParticipantViewModel
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_forgot_password.backButton
import kotlinx.android.synthetic.main.activity_login.*

class ForgotPasswordActivity: CustomAppActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        backButton.setOnClickListener {
            this.finish()
        }
        passwordBtn.setOnClickListener {


            //Make api call to send password

            var email = passwordET.text

            Log.d("zzemail: ",email.toString())


            var participantViewModel = ViewModelProviders.of(this).get(ParticipantViewModel::class.java)
            participantViewModel.init()
            participantViewModel.forgotPassword(email.toString())
            participantViewModel.getUpdatedResponseStatusData().observe(this, Observer {
                it?.let {
//                    loginProgress.visibility = View.GONE
//                    if(it.id != null) {
//                        errorTV.visibility = View.INVISIBLE
//                        Log.d("Login","success")
//                        updateSharedPreferences(it)
//                        val intent = Intent(applicationContext, HomeActivity::class.java)
//                        startActivity(intent)
//                    } else {
//                        errorTV.visibility = View.VISIBLE
//                        errorTV.text = "Email/Password is incorrect"
//                        Toast.makeText(this,"Login error", Toast.LENGTH_SHORT).show()
//                    }
                }
            })

            passwordConfirmationTV.text = "Password has been sent to "+email
            passwordConfirmationTV.visibility = View.VISIBLE
            passwordET.visibility = View.GONE
            passwordBtn.visibility = View.GONE
        }

    }
}