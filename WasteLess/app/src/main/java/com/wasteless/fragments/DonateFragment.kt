/* Copyright (C) Wasteless - All Rights Reserved

 * Unauthorized copying of this file, via any medium is strictly prohibited

 * Proprietary and confidential

 * Written by Siddharth Vijayasankar <sidvijay2004@gmail.com>, January 2021

 */

package com.wasteless.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wasteless.R
import kotlinx.android.synthetic.main.fragment_donate.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.wasteless.activities.HomeActivity
import com.wasteless.model.Donation
import com.wasteless.utils.StringUtils
import com.wasteless.viewmodels.DonationViewModel
import kotlinx.android.synthetic.main.activity_donor_home.*
import java.text.SimpleDateFormat
import java.util.*


class DonateFragment(val homeActivity: HomeActivity)  : Fragment(){


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val activity = getActivity().let{it ->
            val layoutInflater = it!!.getLayoutInflater()
            val view = layoutInflater.inflate(R.layout.fragment_donate, container, false)
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
        val address1 = sharedPreference.getString("address1","--")
        val address2 = sharedPreference.getString("address2","--")
        val city = sharedPreference.getString("city","--")
        val state = sharedPreference.getString("state","--")
        val zipcode = sharedPreference.getString("zipcode","0")
        val phone = sharedPreference.getString("phone","0")
        donate_addressOneET.setText(address1)
        donate_addressTwoET.setText(address2)
        donate_cityET.setText(city)
        donate_stateET.setText(state)
        donate_zipET.setText(zipcode)
        donate_phoneET.setText(phone)
    }

    private fun setButtonActions(){
        donate_donateButton.setOnClickListener {
            hideErrorMessages()
            if(validateFields()){
                val sharedPreference =  this.activity!!.getSharedPreferences(getString(R.string.pref_name), Context.MODE_PRIVATE)
                val donorName = sharedPreference.getString("name","--")
                val donorId = sharedPreference.getString("id","-1")
                val description = donate_descriptionET.text.toString()
                val address1 = donate_addressOneET.text.toString()
                val address2 = donate_addressTwoET.text.toString()
                val city = donate_cityET.text.toString()
                val state = donate_stateET.text.toString()
                val zipcode = donate_zipET.text.toString()
                val phone = donate_phoneET.text.toString()
                val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                val currentDate = sdf.format(Date())
                val donation = Donation(description, currentDate,donorName!!,address1,address2,city,
                    "USA",donorId!!,phone,state,null,"-1","null","Available",zipcode)
                var donationViewModel = ViewModelProviders.of(this).get(DonationViewModel::class.java)
                donationViewModel.init()
                donationViewModel.createDonation(donation)
                donationViewModel.getDonationCreationResponse().observe(this, Observer {
                    it?.let {
                        if(it != null) {
                            Log.e("creation","Created donation")
                            homeActivity.openFragment(MyDonationsFragment(homeActivity))
                                //.bottom_navigation.selectedItemId = R.id.pickup
                        } else {
                            Log.e("error","Couldn't create donation")
                        }
                    }
                })
            }
        }
    }
    private fun validateFields():Boolean {
        hideErrorMessages()
        var flag = true
        if(donate_descriptionET.text!!.isEmpty()){
            flag = false
            donate_descriptionET.error = getString(R.string.field_empty)
        }
        if(donate_addressOneET.text!!.isEmpty()){
            flag = false
            donate_addressOneET.error = getString(R.string.field_empty)
        }
        if(donate_cityET.text!!.isEmpty()){
            flag = false
            donate_cityET.error = getString(R.string.field_empty)
        }
        if(donate_stateET.text!!.isEmpty()){
            flag = false
            donate_stateET.error = getString(R.string.field_empty)
        }

        if(donate_zipET.text!!.isEmpty()){
            flag = false
            donate_zipET.error = getString(R.string.field_empty)
        }

        if(donate_zipET.text!!.isEmpty()){
            flag = false
            donate_phoneET.error = getString(R.string.field_empty)
        }
        if(!StringUtils().isValidState(donate_stateET.text.toString())){
            donate_stateET.error = "Enter a valid state name"
        }
        return flag
    }



    private fun hideErrorMessages(){
        donate_descriptionET.error = null
        donate_addressOneET.error = null
        donate_addressTwoET.error = null
        donate_cityET.error = null
        donate_stateET.error = null
        donate_zipET.error = null
        donate_phoneET.error = null
    }

    companion object {
        //fun newInstance(): DonateFragment = DonateFragment()
    }
}
