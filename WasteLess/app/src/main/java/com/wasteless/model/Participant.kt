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