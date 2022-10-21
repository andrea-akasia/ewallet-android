package com.mobile.ewallet.model.api.credit

import com.google.gson.annotations.SerializedName

data class NominalIncreaseLimit(

	@field:SerializedName("Description")
	val description: String = "",

	@field:SerializedName("Nilai")
	val nilai: String = ""
)
