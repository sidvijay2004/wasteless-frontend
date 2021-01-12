/* Copyright (C) Wasteless - All Rights Reserved

 * Unauthorized copying of this file, via any medium is strictly prohibited

 * Proprietary and confidential

 * Written by Siddharth Vijayasankar <sidvijay2004@gmail.com>, January 2021

 */

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
    val volunteerName: String,
    val status: String,
    val donorZipcode : String
)