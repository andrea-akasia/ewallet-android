package com.mobile.ewallet.model.api.credit

import com.google.gson.annotations.SerializedName

data class PendanaanInfo(

	@field:SerializedName("Message")
	val message: String = "",

	@field:SerializedName("Sub_Message")
	var subMessage: String = "",

	@field:SerializedName("IDPendanaan")
	var iDPendanaan: String = "",

	@field:SerializedName("Mode")
    var mode: String = "",

	@field:SerializedName("Color")
	val color: String = "",

	@field:SerializedName("Title")
	var title: String = "",

	@field:SerializedName("TypePengajuan")
    var typePengajuan: String = ""
)
