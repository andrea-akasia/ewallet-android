package com.mobile.ewallet.model.api.topup

import com.google.gson.annotations.SerializedName

data class TopupInstruction(
	@field:SerializedName("IDBankVA")
	val iDBankVA: String = "",

	@field:SerializedName("Instructions")
	val instructions: String = "",

	@field:SerializedName("ID")
	val iD: String = "",

	@field:SerializedName("Method")
	val method: String = ""
)
