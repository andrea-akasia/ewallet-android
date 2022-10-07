package com.mobile.ewallet.model.api.sendmoney.bycontact

import com.google.gson.annotations.SerializedName

open class BaseSendMoneyContactResponse(
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

    @field:SerializedName("Phone__Beneficiary")
    var destinationPhone: String = ""
)