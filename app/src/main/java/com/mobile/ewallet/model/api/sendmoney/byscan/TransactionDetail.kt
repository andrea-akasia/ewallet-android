package com.mobile.ewallet.model.api.sendmoney.byscan

import com.google.gson.annotations.SerializedName

data class TransactionDetail(
    @field:SerializedName("IDTransaksi")
    val idTransaction: String = "",

    @field:SerializedName("TypeTransaksi")
    val transactionType: String = "",

    @field:SerializedName("MetodeBayar")
    val metodeBayar: String = "",

    @field:SerializedName("NomorReferensi")
    val reffNumber: String = "",

    @field:SerializedName("WaktuTransaksi")
    val time: String = "",

    @field:SerializedName("Jumlah")
    val amount: String = "",

    @field:SerializedName("BiayaAdmin")
    val adminFeeText: String = "",

    @field:SerializedName("TotalPembayaran")
    val total: String = "",

    @field:SerializedName("NamaBeneficiary")
    var name: String = "",

    @field:SerializedName("PhoneBeneficiary")
    var phone: String = "",
): BaseSendMoneyResponse()