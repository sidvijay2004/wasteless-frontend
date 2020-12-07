package com.wasteless.activities

import android.os.Bundle
import android.view.View
import com.wasteless.R
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
            passwordConfirmationTV.text = "Password has been sent to "+email
            passwordConfirmationTV.visibility = View.VISIBLE
            passwordET.visibility = View.GONE
            passwordBtn.visibility = View.GONE
        }

    }
}