package com.wasteless.model

data class Donation(
    val description: String,
    val donationDt: String,
    val donorName: String,
    val donorAddress1: String,
    val donorAddress2: String,
    val donorCity: String,
    val donorCountry: String,
    val donorId: String,
    val donorPhone: String,
    val donorState: String,
    val id: Int?,
    val volunteerId: String,
    val status: String,
    val donorZipcode : String
)