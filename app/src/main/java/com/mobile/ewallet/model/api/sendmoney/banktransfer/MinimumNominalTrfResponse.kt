package com.mobile.ewallet.model.api.sendmoney.banktransfer

import com.google.gson.annotations.SerializedName

data class MinimumNominalTrfResponse(
	@field:SerializedName("Status")
	val status: String = "",

	@field:SerializedName("Message")
	val message: String = "",

	@field:SerializedName("Minimal_Pengiriman")
	val minimalPengiriman: String? = null,

	@field:SerializedName("Minimal_PengirimanRp")
	val minimalPengirimanRp: String? = null
): BaseTransferMoneyResponse()
