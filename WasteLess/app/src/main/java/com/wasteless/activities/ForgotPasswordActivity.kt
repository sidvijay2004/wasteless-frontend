package com.wasteless.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.wasteless.R
import com.wasteless.viewmodels.ParticipantViewModel
import kotlinx.android.synthetic.main.activity_forgot_password.*

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


            passwordConfirmationTV.text = "Password has been sent to "+email
            passwordConfirmationTV.visibility = View.VISIBLE
            passwordET.visibility = View.GONE
            passwordBtn.visibility = View.GONE
        }

    }
}