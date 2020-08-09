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
import com.wasteless.adapters.PickupPostsAdapter
import com.wasteless.model.Participant
import com.wasteless.utils.Utilities
import com.wasteless.viewmodels.DonationViewModel
import kotlinx.android.synthetic.main.fragment_aboutme.*
import kotlinx.android.synthetic.main.fragment_pickup.*

class MyPickupFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_pickup, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadDonations()
    }
    companion object {
        fun newInstance(): MyPickupFragment = MyPickupFragment()
    }
    private fun loadDonations(){
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
            donationViewModel.myPickupList(participant)
            donationViewModel.getDonationsResponse().observe(this, Observer {
                it?.let {
                    postsProgress.visibility = View.GONE
                    if(it != null) {
                        Log.e("Size of list" , it!!.donations!!.size.toString())
//                        pickup_posts_recyclerview.layoutManager = LinearLayoutManager(this.context)
//                        pickup_posts_recyclerview.adapter = PickupPostsAdapter(this,it!!.donations!!.reversed())
                    } else {
                        Log.e("error","Couldn't recieve donations data")
                    }
                }
            })
        }
    }
}