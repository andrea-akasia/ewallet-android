package com.mobile.ewallet.model.api.sendmoney.banktransfer

import com.google.gson.annotations.SerializedName

data class AdminFeeTrfResponse(
	@field:SerializedName("Status")
	val status: String = "",

	@field:SerializedName("Message")
	val message: String = "",

	@field:SerializedName("BiayaAdmin")
	val biayaAdmin: String = ""
): BaseTransferMoneyResponse()
