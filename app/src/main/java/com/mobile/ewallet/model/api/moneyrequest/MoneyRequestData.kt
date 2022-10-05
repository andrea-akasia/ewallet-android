package com.mobile.ewallet.model.api.moneyrequest

import com.google.gson.annotations.SerializedName

data class MoneyRequestData(

	@field:SerializedName("QR_Phone")
	val qRPhone: String? = null,

	@field:SerializedName("QR_Code_Link")
	val qRCodeLink: String? = null,

	@field:SerializedName("QR_Photo")
	val qRPhoto: String? = null,

	@field:SerializedName("QR_Code")
	val qRCode: String? = null,

	@field:SerializedName("QR_Name")
	val qRName: String? = null
)
