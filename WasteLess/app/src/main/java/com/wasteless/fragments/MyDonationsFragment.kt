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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.wasteless.R
import com.wasteless.activities.HomeActivity
import com.wasteless.adapters.MyDonationsAdapter
import com.wasteless.adapters.PickupPostsAdapter
import com.wasteless.model.Participant
import com.wasteless.utils.Utilities
import com.wasteless.viewmodels.DonationViewModel
import kotlinx.android.synthetic.main.fragment_aboutme.*
import kotlinx.android.synthetic.main.fragment_mydonations.*
import kotlinx.android.synthetic.main.fragment_mypickup.*
import kotlinx.android.synthetic.main.fragment_pickup.*
import kotlinx.android.synthetic.main.fragment_pickup.pickup_posts_recyclerview
import kotlinx.android.synthetic.main.fragment_pickup.postsProgress

class MyDonationsFragment(val context: HomeActivity): Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_mydonations, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadDonations()
        donateButton.setOnClickListener {
            context.openDonateFragment(DonateFragment(context))
        }
    }

    fun loadDonations(){
        postsProgress.visibility = View.VISIBLE
        val sharedPreference =  this.activity!!.getSharedPreferences(getString(R.string.pref_name), Context.MODE_PRIVATE)
        val name = sharedPreference.getString("name","0")
        val id = sharedPreference.getString("id","0")
        val password = sharedPreference.getString("password","--")
        val email = sharedPreference.getString("email","--")
        val address1 = ""
        val address2 = ""
        val city = ""
        val state = ""
        val zipcode = ""
        val phone = ""
        val participant = Participant(address1,address2,city,"usa",email!!,id!!.toInt(),name!!,password!!,phone,state,zipcode)

        if(Utilities().isInternetAvailable()){
            var donationViewModel = ViewModelProviders.of(this).get(DonationViewModel::class.java)
            donationViewModel.init()
//            donationViewModel.getDonations()
            donationViewModel.myDonationsList(participant)
            donationViewModel.getDonationsResponse().observe(this, Observer {
                it?.let {
                    postsProgress.visibility = View.GONE
                    if(it != null) {
                        Log.e("Size of list" , it!!.donations!!.size.toString())
                        if(it!!.donations!!.isEmpty()){
                            noDataLayout_mydonations.visibility = View.INVISIBLE
                        } else {
                            noDataLayout_mydonations.visibility = View.VISIBLE
                        }
                        pickup_posts_recyclerview.layoutManager = LinearLayoutManager(this.context)
                        pickup_posts_recyclerview.adapter = MyDonationsAdapter(this,it!!.donations!!.reversed())
                    } else {
                        Log.e("error","Couldn't recieve donations data")
                        noDataLayout_mypickup.visibility = View.VISIBLE
                    }
                }
            })
        }
    }
}