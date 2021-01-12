/* Copyright (C) Wasteless - All Rights Reserved

 * Unauthorized copying of this file, via any medium is strictly prohibited

 * Proprietary and confidential

 * Written by Siddharth Vijayasankar <sidvijay2004@gmail.com>, January 2021

 */

package com.wasteless.model

data class Participant(
    val address1: String,
    val address2: String,
    val city: String,
    val country: String,
    val email: String,
    val id: Int?,
    val name: String,
    val password: String,
    val phone: String,
    val state: String,
    val zipcode: String
)