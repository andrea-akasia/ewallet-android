package com.mobile.ewallet.model.api.credit.billing

import com.google.gson.annotations.SerializedName

data class BillingVA(

	@field:SerializedName("IDMember")
	val iDMember: String,

	@field:SerializedName("NamaVA")
	val namaVA: String,

	@field:SerializedName("TotalTagihan")
	val totalTagihan: String,

	@field:SerializedName("Keterangan")
	val keterangan: String,

	@field:SerializedName("IDBank")
	val iDBank: String,

	@field:SerializedName("NOVA")
	val nOVA: String,

	@field:SerializedName("ID")
	val iD: String,

	@field:SerializedName("NamaBank")
	val namaBank: String,

	@field:SerializedName("Logo")
	val logo: String
)
