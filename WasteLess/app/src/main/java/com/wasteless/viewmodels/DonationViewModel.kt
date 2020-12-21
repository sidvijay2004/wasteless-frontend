package com.wasteless.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wasteless.model.Donation
import com.wasteless.model.DonationList
import com.wasteless.model.Participant
import com.wasteless.networking.WastelessRepository

class DonationViewModel : ViewModel() {

    var donationData : MutableLiveData<Donation>? = null
    var donations : MutableLiveData<DonationList>? = null
    var wastelessRepository: WastelessRepository? = null

    fun init() {
        if(donationData != null) {
            return
        }
        wastelessRepository = WastelessRepository().getInstance()
    }

    fun createDonation(donation: Donation) {
        donationData = wastelessRepository!!.createDonation(donation)
    }


    fun getDonationCreationResponse(): LiveData<Donation> {
        return donationData as LiveData<Donation>
    }

    fun getDonationsResponse(): LiveData<DonationList>{
        return donations as LiveData<DonationList>
    }

    fun getDonations(){
        donations = wastelessRepository!!.getDonations()
    }
    fun getPickupList(participant: Participant){
        donations = wastelessRepository!!.getPickupList(participant)
    }
    fun myPickupList(participant: Participant){
        donations = wastelessRepository!!.myPickupList(participant)
    }
    fun myDonationsList(participant: Participant){
        donations = wastelessRepository!!.myDonationsList(participant)
    }
    fun updateTakenDonation(donationId: Int , participantId: Int){
        donationData = wastelessRepository!!.updateTakenDonation(donationId,participantId)
    }
    fun cancelTakenDonation(donationId: Int , participantId: Int){
        donationData = wastelessRepository!!.cancelTakenDonation(donationId,participantId)
    }
    fun completedDonation(donationId: Int , participantId: Int){
        donationData = wastelessRepository!!.completedDonation(donationId,participantId)
    }

    fun deleteDonation(donationId: Int , participantId: Int){
        donationData = wastelessRepository!!.deleteDonation(donationId,participantId)
    }

}