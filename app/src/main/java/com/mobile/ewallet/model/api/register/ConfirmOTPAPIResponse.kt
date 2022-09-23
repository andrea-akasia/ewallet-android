package com.mobile.ewallet.model.api.register

import com.google.gson.annotations.SerializedName

data class ConfirmOTPAPIResponse(

	@field:SerializedName("Status")
	val status: String? = null,

	@field:SerializedName("IDMember")
	val iDMember: String? = null,

	@field:SerializedName("Message")
	val message: String? = null,

	@field:SerializedName("NOWA")
	val nOWA: String? = null,

	@field:SerializedName("Nama")
	val name: String? = null
)
