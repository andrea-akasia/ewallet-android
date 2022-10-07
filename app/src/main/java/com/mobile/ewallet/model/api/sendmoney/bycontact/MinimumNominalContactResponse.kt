package com.mobile.ewallet.model.api.sendmoney.bycontact

import com.google.gson.annotations.SerializedName

data class MinimumNominalContactResponse(
    @field:SerializedName("Minimal_Pengiriman")
    val minimal: String = ""
): BaseSendMoneyContactResponse()