package com.mobile.ewallet.model.api.credit

import com.google.gson.annotations.SerializedName

data class IncreaseLimitInfo(

	@field:SerializedName("Form_Intro")
	val formIntro: String = "",

	@field:SerializedName("Message")
	val message: String = "",

	@field:SerializedName("Sub_Message")
	val subMessage: String = "",

	@field:SerializedName("Mode")
    var mode: String = "",

	@field:SerializedName("Color")
	val color: String = "",

	@field:SerializedName("Title")
	val title: String = ""
)
