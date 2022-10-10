package com.mobile.ewallet.model.api.credit

import com.google.gson.annotations.SerializedName

data class JenisKreditParameter(
	@field:SerializedName("MinTenor")
	val minTenor: String = "",

	@field:SerializedName("MinLimit")
	val minLimit: String = "",

	@field:SerializedName("MaxTenor")
	val maxTenor: String = "",

	@field:SerializedName("MaxLimit")
	val maxLimit: String = ""
)
