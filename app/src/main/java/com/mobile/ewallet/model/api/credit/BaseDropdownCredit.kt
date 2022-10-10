package com.mobile.ewallet.model.api.credit

import com.google.gson.annotations.SerializedName

open class BaseDropdownCredit(

	@field:SerializedName("Description")
	var description: String = "",

	@field:SerializedName("Code")
	var code: String = ""
)
