package com.wasteless.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wasteless.R
import com.wasteless.model.Donation
import kotlinx.android.synthetic.main.cell_donationpost.view.*

import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat

class MyDonationsAdapter(val context: Context, val donations: List<Donation>) : RecyclerView.Adapter<MyDonationsAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cell_donationpost,parent,false)
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

//        holder.pickupBtn.setOnClickListener {
//                            MaterialAlertDialogBuilder(context)
//                    .setTitle("Confirm")
//                   .setMessage("Are you sure that you can pick "+position+" up under 30 minutes?")
//                    .setPositiveButton("Yes", null)
//                    .setNegativeButton("No",null)
//                    .show()
//        }
    }

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return donations.size
    }

    class CustomViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val name = view.postcell_donorName
//        val pickupBtn = view.postcell_pickupBtn
        val timeAndDate = view.postcell_date
        val description = view.postcell_description
        val address  = view.postcell_address
    }
}
