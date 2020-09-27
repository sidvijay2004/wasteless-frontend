package com.wasteless.adapters

import android.content.Context
import android.content.DialogInterface
import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.wasteless.R
import com.wasteless.fragments.MyPickupFragment
import com.wasteless.fragments.PickupFragment
import com.wasteless.model.Donation
import com.wasteless.viewmodels.DonationViewModel
import kotlinx.android.synthetic.main.cell_donationpost.view.*
import java.text.SimpleDateFormat

class MyPickupPostsAdapter(val context: MyPickupFragment, val donations: List<Donation>) : RecyclerView.Adapter<MyPickupPostsAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(context.context).inflate(R.layout.cell_donationpost,parent,false)
        Log.e("List size - ",donations.size.toString())
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        Log.e("Position - ",position.toString())
        val donation = donations.get(position)

        holder.name.text = donation.donorName
        holder.description.text = donation.description
        val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").parse(donation.donationDt)
        val sdf = SimpleDateFormat("MMM dd, HH:mm aa")
        val formattedDate = sdf.format(date)
        holder.timeAndDate.text = formattedDate
        val address = donation.donorAddress1 + "," + donation.donorAddress2 + "," + donation.donorCity
        holder.address.text = address
        holder.pickupBtn.visibility = View.GONE
        holder.mydonationlayout.visibility = View.GONE
        holder.cancelLayout.visibility = View.VISIBLE
        holder.cancelLayout.setOnClickListener {
            MaterialAlertDialogBuilder(context.context)
                .setTitle("Confirm")
                .setMessage("Are you sure that you want to cancel your pickup?")
                .setPositiveButton("Yes", DialogInterface.OnClickListener {
                        dialog, id -> cancelPickup(position)
                })
                .setNegativeButton("No",null)
                .show()
        }

    }

    //    fun makeApiCall(position: Int){ makeApiCall(position)
    fun cancelPickup(position: Int){
        var donationViewModel = ViewModelProviders.of(this.context).get(DonationViewModel::class.java)

        val sharedPreference =  this.context.context!!.getSharedPreferences("com.wasteless", Context.MODE_PRIVATE)
        val participantId = sharedPreference.getString("id","0")

        donationViewModel.init()
        donationViewModel.updateTakenDonation(donations.get(position).id!!, participantId!!.toInt())
        donationViewModel.getDonationCreationResponse().observe(this.context, Observer {
            it?.let {
                if(it != null) {
                    Log.e("Update Taken","successfull")
                    context.loadDonations()
                } else {
                    Log.e("error","Error updating Taken")
                }
            }
        })
    }



    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return donations.size
    }

    class CustomViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val name = view.postcell_donorName
        val pickupBtn = view.postcell_pickupBtn
        val timeAndDate = view.postcell_date
        val description = view.postcell_description
        val mydonationlayout = view.postcell_mydonation
        val address  = view.postcell_address
        val cancelBtn = view.pickup_cancel
        val doneBtn = view.pickup_done
        val cancelLayout = view.postcell_cancel
        val pickupStatus = view.pickup_status
    }
}
