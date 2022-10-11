package com.mobile.ewallet.model.api.credit

import com.google.gson.annotations.SerializedName

data class KUMFulfillmentResponse(
    @field:SerializedName("Status")
    val status: String = "",

    @field:SerializedName("Message")
    val message: String = "",

    @field:SerializedName("STEP")
    val sTEP: String = ""
)