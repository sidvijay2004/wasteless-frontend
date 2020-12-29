package com.wasteless.adapters

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.wasteless.R
import com.wasteless.model.Donation
import kotlinx.android.synthetic.main.cell_donationpost.view.*

import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.wasteless.fragments.MyDonationsFragment
import com.wasteless.viewmodels.DonationViewModel
import java.text.SimpleDateFormat

class MyDonationsAdapter(val context: MyDonationsFragment, val donations: List<Donation>) : RecyclerView.Adapter<MyDonationsAdapter.CustomViewHolder>() {
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
        holder.mydonationlayout.visibility = View.VISIBLE
        holder.pickupBtn.visibility = View.GONE
        holder.pickupStatus.visibility = View.VISIBLE
        if(donation.volunteerId != null && !donation.volunteerId.equals("-1")){
            holder.pickupStatus.setTextColor(Color.parseColor("#BB0000"))
            holder.pickupStatus.text = "Status : Picked up by "+donation.volunteerId
        } else {
            holder.pickupStatus.setTextColor(Color.parseColor("#76BA1B"))
            holder.pickupStatus.text = "Status : Available"
        }
        holder.deleteButtonLayout.visibility = View.VISIBLE
        holder.cancelBtn.setOnClickListener {
            MaterialAlertDialogBuilder(context.context)
                .setTitle("Confirm")
                .setMessage("Do you want to cancel this donation?")
                .setPositiveButton("Yes", DialogInterface.OnClickListener {
                        dialog, id -> cancelDonation(position)
                })
                .setNegativeButton("No",null)
                .show()
        }
        holder.doneBtn.setOnClickListener {
            MaterialAlertDialogBuilder(context.context)
                .setTitle("Confirm")
                .setMessage("Did a volunteer pick up your donation?")
                .setPositiveButton("Yes", DialogInterface.OnClickListener {
                        dialog, id -> completeDonation(position)
                })
                .setNegativeButton("No",null)
                .show()
        }
        holder.deleteButtonLayout.setOnClickListener {
            MaterialAlertDialogBuilder(context.context)
                .setTitle("Delete")
                .setMessage("Do you want to delete this donation?")
                .setPositiveButton("Yes", DialogInterface.OnClickListener {
                        dialog, id -> deleteDonation(position)
                })
                .setNegativeButton("No",null)
                .show()
        }
    }


    fun cancelDonation(position: Int){
        var donationViewModel = ViewModelProviders.of(this.context).get(DonationViewModel::class.java)

        val sharedPreference =  this.context.context!!.getSharedPreferences("com.wasteless", Context.MODE_PRIVATE)
        val participantId = sharedPreference.getString("id","0")

        donationViewModel.init()
        donationViewModel.cancelTakenDonation(donations.get(position).id!!, participantId!!.toInt())
        donationViewModel.getDonationCreationResponse().observe(this.context, Observer {
            it?.let {
                if(it != null) {
                    context.loadDonations()
                    Log.e("Update Taken","successfull")
                    Log.e("Cancel Taken Donation","successfull")
                } else {
                    Log.e("error","Error updating Taken")
                }
            }
        })
    }


    fun completeDonation(position: Int){
        var donationViewModel = ViewModelProviders.of(this.context).get(DonationViewModel::class.java)

        val sharedPreference =  this.context.context!!.getSharedPreferences("com.wasteless", Context.MODE_PRIVATE)
        val participantId = sharedPreference.getString("id","0")

        donationViewModel.init()
        donationViewModel.completedDonation(donations.get(position).id!!, participantId!!.toInt())
        donationViewModel.getDonationCreationResponse().observe(this.context, Observer {
            it?.let {
                if(it != null) {
                    context.loadDonations()
                    Log.e("Update Taken","successfull")
                } else {
                    Log.e("error","Error updating Taken")
                }
            }
        })
    }

    fun deleteDonation(position: Int){
        var donationViewModel = ViewModelProviders.of(this.context).get(DonationViewModel::class.java)

        val sharedPreference =  this.context.context!!.getSharedPreferences("com.wasteless", Context.MODE_PRIVATE)
        val participantId = sharedPreference.getString("id","0")

        donationViewModel.init()
        donationViewModel.deleteDonation(donations.get(position).id!!, participantId!!.toInt())
        donationViewModel.getDonationCreationResponse().observe(this.context, Observer {
            it?.let {
                if(it != null) {
                    context.loadDonations()
                    Log.e("Delete  Donation","successfull")
                } else {
                    Log.e("error","Error Deleting Donation")
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
        val address  = view.postcell_address
        val mydonationlayout = view.postcell_mydonation
        val cancelBtn = view.pickup_cancel
        val doneBtn = view.pickup_done
        val deleteButtonLayout = view.deleteButtonLayout
        val deleteButton = view.delete_button
        val pickupStatus = view.pickup_status
    }
}
