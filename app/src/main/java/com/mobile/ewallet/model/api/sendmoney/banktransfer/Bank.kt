package com.mobile.ewallet.model.api.sendmoney.banktransfer

import com.google.gson.annotations.SerializedName

data class Bank(

	@field:SerializedName("IDRecord")
	val iDRecord: Int? = null,

	@field:SerializedName("Keterangan")
	val keterangan: String? = null,

	@field:SerializedName("ID")
	val iD: String? = null
)
