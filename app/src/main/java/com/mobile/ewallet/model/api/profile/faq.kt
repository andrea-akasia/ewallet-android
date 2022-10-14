package com.mobile.ewallet.model.api.profile

import com.google.gson.annotations.SerializedName

data class Faq(
	@field:SerializedName("Answer")
	val answer: String = "",

	@field:SerializedName("Question")
	val question: String = "",

	@field:SerializedName("ID")
	val iD: String = ""
)
