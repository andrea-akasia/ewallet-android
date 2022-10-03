package com.mobile.ewallet.model.api.dashboard

import com.google.gson.annotations.SerializedName

data class TransactionItem(

	@field:SerializedName("Debit_Credit")
	val debitCredit: String? = null,

	@field:SerializedName("TypeTransaksi")
	val typeTransaksi: String? = null,

	@field:SerializedName("Title")
	val title: String? = null,

	@field:SerializedName("ID")
	val iD: String? = null,

	@field:SerializedName("Icon")
	val icon: String? = null,

	@field:SerializedName("Timestamp")
	val timestamp: String? = null,

	@field:SerializedName("Nominal")
	val nominal: String? = null,

	@field:SerializedName("IDrecord")
	val iDrecord: Int? = null
)
