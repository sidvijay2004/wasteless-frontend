package com.wasteless.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wasteless.model.LoginCredential
import com.wasteless.model.Participant
import com.wasteless.model.ResponseStatus
import com.wasteless.networking.WastelessRepository

class ParticipantViewModel: ViewModel() {

    var participantData : MutableLiveData<Participant>? = null
    var updatedParticipantData : MutableLiveData<Participant>? = null
    var wastelessRepository: WastelessRepository? = null
    var updatedResponseStatusData : MutableLiveData<ResponseStatus>? = null

    fun init() {
        if(participantData != null) {
            return
        }
        wastelessRepository = WastelessRepository().getInstance()
    }

    fun createParticipant(participant: Participant) {
        participantData = wastelessRepository!!.createParticipant(participant)
    }

    fun updateParticipant(participant: Participant){
        updatedParticipantData = wastelessRepository!!.updateParticipant(participant)
    }

    fun getUpdatedParticipantData():LiveData<Participant>{
        return updatedParticipantData as LiveData<Participant>
    }




    fun getParticipantData():LiveData<Participant>{
        return participantData as LiveData<Participant>
    }

    fun validateCredentials(credentials: LoginCredential){
        participantData = wastelessRepository!!.validateLoginCredentials(credentials)
    }

    fun getParticipant(participantId: Int): MutableLiveData<Participant> {
        return wastelessRepository!!.getParticipant(participantId)
    }
    fun getUpdatedResponseStatusData():LiveData<ResponseStatus>{
        return updatedResponseStatusData as LiveData<ResponseStatus>
    }
    fun forgotPassword(email: String){
        updatedResponseStatusData = wastelessRepository!!.forgotPassword(email)
    }
    }
