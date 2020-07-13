package com.wasteless.model

import com.google.gson.annotations.SerializedName



class DonationList {

    @SerializedName("content")
    public val donations: List<Donation>? = null
}