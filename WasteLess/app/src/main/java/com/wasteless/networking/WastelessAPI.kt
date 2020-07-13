package com.wasteless.networking

import com.wasteless.model.Donation
import com.wasteless.model.DonationList
import com.wasteless.model.LoginCredential
import com.wasteless.model.Participant
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.PUT



interface WastelessAPI  {

    @POST("donors")
    fun createParticipant(@Body participant: Participant): Call<Participant>

    @POST("donations")
    fun createDonation(@Body donation: Donation): Call<Donation>

    @GET("donations")
    fun getDonations():Call<DonationList>

    @PUT("donors/{donorId}")
    fun updateParticipant(@Body participant: Participant, @Path( "donorId") id: Int): Call<Participant>

    // updation for picking up donation
    @PUT("donations/{donationId}")
    fun updateDonation(@Body donation: Donation, @Path( "donationId") id: Int): Call<Donation>

    @POST("/donors/login")
    fun validateLoginCredentials(@Body credentials: LoginCredential) : Call<Participant>

}