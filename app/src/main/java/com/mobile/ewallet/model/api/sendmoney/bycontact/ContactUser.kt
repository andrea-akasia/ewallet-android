package com.mobile.ewallet.model.api.sendmoney.bycontact

import com.google.gson.annotations.SerializedName

data class ContactUser(
	@field:SerializedName("Nama")
	val nama: String = "",

	@field:SerializedName("Phone")
	val phone: String = "",

	@field:SerializedName("Photo")
	val photo: String = "",

	@field:SerializedName("ID")
	val iD: String = "",

	@field:SerializedName("Thumbnail")
	val thumbnail: String = ""
)
