package com.mobile.ewallet.model.api.sendmoney.byscan

import com.google.gson.annotations.SerializedName

data class AdminFeeResponse(
    @field:SerializedName("Jumlah")
    val amount: String = "",

    @field:SerializedName("BiayaAdminTEXT")
    val adminFeeText: String = "",

    @field:SerializedName("BiayaAdmin")
    val adminFee: String = "",

    @field:SerializedName("TotalPembayaran")
    val total: String = "",
): BaseSendMoneyResponse()