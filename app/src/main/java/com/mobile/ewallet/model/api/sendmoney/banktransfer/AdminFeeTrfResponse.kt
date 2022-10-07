package com.mobile.ewallet.model.api.sendmoney.banktransfer

import com.google.gson.annotations.SerializedName

data class AdminFeeTrfResponse(
	@field:SerializedName("BiayaAdmin")
	val biayaAdmin: String = "",

	@field:SerializedName("BiayaAdmin1")
	val biayaAdmin1: String = ""
): BaseTransferMoneyResponse()
