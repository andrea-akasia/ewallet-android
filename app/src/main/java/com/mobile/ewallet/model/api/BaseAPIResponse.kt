package com.mobile.ewallet.model.api

import com.google.gson.annotations.SerializedName

data class BaseAPIResponse(

	@field:SerializedName("Status")
	val status: String? = null,

	@field:SerializedName("Message")
	val message: String? = null
)
