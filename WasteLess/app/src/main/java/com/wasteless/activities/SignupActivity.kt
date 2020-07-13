package com.wasteless.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.wasteless.R
import com.wasteless.model.Participant
import com.wasteless.utils.StringUtils
import com.wasteless.utils.Utilities
import kotlinx.android.synthetic.main.activity_signup.*
import com.wasteless.viewmodels.ParticipantViewModel


class SignupActivity: CustomAppActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(com.wasteless.R.layout.activity_signup)
        setButtonActions()
        hideErrorMessages()
        //nameET.error = "Not empty"
        //setUpAutocompleteTextview()                           //Remove before production
    }

    private fun setUpAutocompleteTextview() {
        val adapter = ArrayAdapter<String>(this,R.layout.custom_list_item, R.id.text_view_list_item,StringUtils().states)
        //stateET.setAdapter(adapter)
    }

    private fun setButtonActions() {
        backButton.setOnClickListener {
            this.finish()
        }
        signUpButton.setOnClickListener {
            if(validateFields()){
                signupProgress.visibility = View.VISIBLE
                val name = nameET.text.toString()
                val email = emailET.text.toString()
                val password = passwordET.text.toString()
                val address1 = addressOneET.text.toString()
                val address2 = addressTwoET.text.toString()
                val city = cityET.text.toString()
                val state = stateET.text.toString()
                val zipcode = zipET.text.toString()
                val phone = phoneET.text.toString()
                val participant = Participant(address1,address2,city,"usa",email,null,name,password,phone,state,zipcode)
                if(Utilities().isInternetAvailable()){
                    var participantViewModel = ViewModelProviders.of(this).get(ParticipantViewModel::class.java)
                    participantViewModel.init()
                    participantViewModel.createParticipant(participant)
                    participantViewModel.getParticipantData().observe(this, Observer {
                        it?.let {
                            signupProgress.visibility = View.GONE
                            if(it.id != null) {
                                Log.d("Successfully added","bla bla bla")
                                updateSharedPreferences(it)
                                val intent = Intent(applicationContext, HomeActivity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this,"Sign up error",Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
                }
            }
        }
    }

    private fun updateSharedPreferences(participant:Participant) {
        val sharedPreference =  getSharedPreferences(getString(R.string.pref_name), Context.MODE_PRIVATE)
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
        editor.commit()
    }

    private fun hideErrorMessages(){
        nameET.error = null
        emailET.error = null
        passwordET.error = null
        retypePasswordET.error = null
        addressOneET.error = null
        addressTwoET.error = null
        cityET.error = null
        stateET.error = null
        zipET.error = null
        phoneET.error = null
    }

    private fun validateFields():Boolean {
        hideErrorMessages()
        var flag = true
        if(nameET.text!!.isEmpty()){
            flag = false
            nameET.error = getString(R.string.field_empty)
        }
        if(emailET.text!!.isEmpty()){
            flag = false
            emailET.error = getString(R.string.field_empty)
        }
        if(passwordET.text!!.isEmpty()){
            flag = false
           passwordET.error= getString(R.string.field_empty)
        }
        if(retypePasswordET.text!!.isEmpty()){
            flag = false
            retypePasswordET.error = getString(R.string.field_empty)
        }
        if(addressOneET.text!!.isEmpty()){
            flag = false
            addressOneET.error = getString(R.string.field_empty)
        }
        if(stateET.text!!.isEmpty()){
            flag = false
            stateET.error = getString(R.string.field_empty)
        }
        if(cityET.text!!.isEmpty()){
            flag = false
            cityET.error = getString(R.string.field_empty)
        }

        if(phoneET.text!!.isEmpty()){
            flag = false
            phoneET.error = getString(R.string.field_empty)
        }

        if(zipET.text!!.isEmpty()){
            flag = false
            zipET.error = getString(R.string.field_empty)
        }
        if((!passwordET.text!!.isEmpty() && !retypePasswordET.text!!.isEmpty()) ){
            if((passwordET.text.toString() != retypePasswordET.text.toString())){
                flag = false
                retypePasswordET.error = "Passwords do not match"
            }
        }
        if(!StringUtils().isValidState(stateET.text.toString())){
            stateET.error = "Enter a valid state name"
            flag = false
        }
        return flag
    }
}