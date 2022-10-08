package com.mobile.ewallet.model.api.topup

import com.google.gson.annotations.SerializedName

data class TopupVA(
	@field:SerializedName("Status")
	val status: Int = 0,

	@field:SerializedName("IDMember")
	val iDMember: String = "",

	@field:SerializedName("NamaVA")
	val namaVA: String = "",

	@field:SerializedName("Keterangan")
	val keterangan: String = "",

	@field:SerializedName("MininalTopup")
	val mininalTopup: String = "",

	@field:SerializedName("IDBank")
	val iDBank: String = "",

	@field:SerializedName("NOVA")
	val nOVA: String = "",

	@field:SerializedName("ID")
	val iD: String = "",

	@field:SerializedName("NamaBank")
	val namaBank: String = "",

	@field:SerializedName("Logo")
	val logo: String = ""
)
