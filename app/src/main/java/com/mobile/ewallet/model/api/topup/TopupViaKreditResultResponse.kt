package com.mobile.ewallet.model.api.topup

import com.google.gson.annotations.SerializedName

data class TopupViaKreditResultResponse(

	@field:SerializedName("Status")
	val status: String,

	@field:SerializedName("NominalKirim")
	val nominalKirim: String,

	@field:SerializedName("Message")
	val message: String,

	@field:SerializedName("Waktu")
	val waktu: String,

	@field:SerializedName("TypeTransaksi")
	val typeTransaksi: String,

	@field:SerializedName("MetodeBayar")
	val metodeBayar: String,

	@field:SerializedName("IDTransaksi")
	val iDTransaksi: String
)
