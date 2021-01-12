/* Copyright (C) Wasteless - All Rights Reserved

 * Unauthorized copying of this file, via any medium is strictly prohibited

 * Proprietary and confidential

 * Written by Siddharth Vijayasankar <sidvijay2004@gmail.com>, January 2021

 */

package com.wasteless.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.wasteless.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wasteless.fragments.*
import kotlinx.android.synthetic.main.activity_donor_home.*

class HomeActivity: CustomAppActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donor_home)
        bottom_navigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener() {
                it ->
            when(it.itemId) {
                R.id.aboutme -> {
                    openFragment(AboutMeFragment.newInstance())
                }
//                R.id.donate -> {
//                    openDonateFragment(DonateFragment(this))
//                }
                R.id.pickup -> {
                    openFragment(PickupFragment.newInstance())
                }
                R.id.mypickup -> {
                    openFragment(MyPickupFragment.newInstance())
                }
                R.id.mydonations -> {
                    openFragment(MyDonationsFragment(this))
                }
            }
            true
        })
        bottom_navigation.selectedItemId = R.id.pickup
    }

    fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun openDonateFragment(fragment: DonateFragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    //bottom_navigation
}