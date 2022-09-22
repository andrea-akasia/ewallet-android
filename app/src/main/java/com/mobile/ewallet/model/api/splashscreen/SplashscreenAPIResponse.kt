package com.mobile.ewallet.model.api.splashscreen

import com.google.gson.annotations.SerializedName

data class SplashscreenAPIResponse(

	@field:SerializedName("Status")
	val status: Int? = null,

	@field:SerializedName("Message")
	val message: String? = null,

	@field:SerializedName("Title")
	val title: String? = null,

	@field:SerializedName("ID")
	val iD: Int? = null,

	@field:SerializedName("Image")
	val image: String? = null
)
