/* Copyright (C) Wasteless - All Rights Reserved

 * Unauthorized copying of this file, via any medium is strictly prohibited

 * Proprietary and confidential

 * Written by Siddharth Vijayasankar <sidvijay2004@gmail.com>, January 2021

 */

package com.wasteless.model

import com.google.gson.annotations.SerializedName



class DonationList {

    @SerializedName("content")
    public val donations: List<Donation>? = null
}