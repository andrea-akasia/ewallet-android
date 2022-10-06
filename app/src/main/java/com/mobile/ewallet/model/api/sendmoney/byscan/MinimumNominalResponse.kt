package com.mobile.ewallet.model.api.sendmoney.byscan

import com.google.gson.annotations.SerializedName

data class MinimumNominalResponse(
    @field:SerializedName("Minimal_Pengiriman")
    val minimal: String = "",

    @field:SerializedName("Minimal_PengirimanRp")
    val minimalText: String = ""
): BaseSendMoneyResponse()