package com.wasteless.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.wasteless.R
import com.wasteless.activities.LoginActivity
import com.wasteless.model.Participant
import com.wasteless.utils.StringUtils
import com.wasteless.utils.Utilities
import com.wasteless.viewmodels.ParticipantViewModel
import kotlinx.android.synthetic.main.fragment_aboutme.*

class AboutMeFragment : Fragment(){


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val activity = getActivity().let{it ->
            val layoutInflater = it!!.getLayoutInflater()
            val view = layoutInflater.inflate(R.layout.fragment_aboutme, container, false)
            return view
        }
        return inflater.inflate(R.layout.activity_errorpage, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        hideErrorMessages()
        setButtonActions()
        setUserDetails()
    }

    private fun setUserDetails() {

        val sharedPreference =  this.activity!!.getSharedPreferences(getString(R.string.pref_name), Context.MODE_PRIVATE)
        val name = sharedPreference.getString("name","--")
        val address1 = sharedPreference.getString("address1","--")
        val address2 = sharedPreference.getString("address2","--")
        val city = sharedPreference.getString("city","--")
        val state = sharedPreference.getString("state","--")
        val zipcode = sharedPreference.getString("zipcode","0")
        val phone = sharedPreference.getString("phone","0")
        aboutme_nameET.setText(name)
        aboutme_addressOneET.setText(address1)
        aboutme_addressTwoET.setText(address2)
        aboutme_cityET.setText(city)
        aboutme_stateET.setText(state)
        aboutme_zipET.setText(zipcode)
        aboutme_phoneET.setText(phone)
    }

    private fun setButtonActions() {
        aboutme_updateButton.setOnClickListener {
            hideErrorMessages()
            if(validateFields()){
                val sharedPreference =  this.activity!!.getSharedPreferences(getString(R.string.pref_name), Context.MODE_PRIVATE)
                val name = aboutme_nameET.text!!.toString()
                val id = sharedPreference.getString("id","0")
                val password = sharedPreference.getString("password","--")
                val email = sharedPreference.getString("email","--")
                val address1 = aboutme_addressOneET.text!!.toString()
                val address2 = aboutme_addressOneET.text!!.toString()
                val city = aboutme_cityET.text!!.toString()
                val state = aboutme_stateET.text!!.toString()
                val zipcode = aboutme_zipET.text!!.toString()
                val phone = aboutme_phoneET.text!!.toString()
                val participant = Participant(address1,address2,city,"usa",email!!,id!!.toInt(),name!!,password!!,phone,state,zipcode)
                if(Utilities().isInternetAvailable()){
                    var participantViewModel = ViewModelProviders.of(this).get(ParticipantViewModel::class.java)
                    participantViewModel.init()
                    participantViewModel.updateParticipant(participant)
                    participantViewModel.getUpdatedParticipantData().observe(this, Observer {
                        it?.let {
                            if(it.id != null) {
                                Log.d("Successfully updated","bla bla bla")
                                updateSharedPreferences(it)
                            } else {
                            }
                        }
                    })
                }
            }
        }
        aboutme_logoutButton.setOnClickListener {

            val sharedPreference =  this.activity!!.getSharedPreferences(getString(R.string.pref_name), Context.MODE_PRIVATE)
            var editor = sharedPreference.edit()
            editor.clear()
            editor.commit()
            val intent = Intent(this.context, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateSharedPreferences(participant: Participant) {
        val sharedPreference =  context!!.getSharedPreferences(getString(R.string.pref_name), Context.MODE_PRIVATE)
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

    private fun validateFields(): Boolean {
        var flag = true
        if(aboutme_nameET.text!!.isEmpty()){
            aboutme_nameET.error = getString(R.string.field_empty)
            flag = false
        }
        if(aboutme_addressOneET.text!!.isEmpty()){
            aboutme_addressOneET.error = getString(R.string.field_empty)
            flag = false
        }
        if(aboutme_cityET.text!!.isEmpty()){
            aboutme_cityET.error = getString(R.string.field_empty)
            flag = false
        }
        if(aboutme_stateET.text!!.isEmpty()){
            aboutme_stateET.error = getString(R.string.field_empty)
            flag = false
        }
        if(aboutme_zipET.text!!.isEmpty()){
            aboutme_zipET.error = getString(R.string.field_empty)
            flag = false
        }
        if(aboutme_phoneET.text!!.isEmpty()){
            aboutme_phoneET.error = getString(R.string.field_empty)
            flag = false
        }
        if(!StringUtils().isValidState(aboutme_stateET.text!!.toString())){
            aboutme_stateET.error = "Enter a valid state"
            flag = false
        }
        return flag
    }

    private fun hideErrorMessages() {
        aboutme_addressOneET.error = null
        aboutme_addressTwoET.error = null
        aboutme_cityET.error = null
        aboutme_stateET.error = null
        aboutme_zipET.error = null
        aboutme_phoneET.error = null

    }

    companion object {
            fun newInstance(): AboutMeFragment = AboutMeFragment()
        }
}
