package com.mobile.ewallet.model.api.sendmoney

import com.google.gson.annotations.SerializedName

open class BaseSendMoneyResponse(
    @field:SerializedName("Status")
    var status: String = "",

    @field:SerializedName("Message")
    var message: String = "",

    @field:SerializedName("Nama_Beneficiary")
    var destinationName: String = "",

    @field:SerializedName("Thumbnail")
    var destinationThumbnail: String = "",

    @field:SerializedName("Photo")
    var destinationPhoto: String = "",

    @field:SerializedName("Phone_Beneficiary")
    var destinationPhone: String = ""
)