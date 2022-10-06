package com.mobile.ewallet.model.api.sendmoney

import com.google.gson.annotations.SerializedName

data class HistoryTransferTransaction(

	@field:SerializedName("IDMember")
	val iDMember: String? = null,

	@field:SerializedName("IDMember_Beneficiary")
	val iDMemberBeneficiary: String? = null,

	@field:SerializedName("Nama")
	val nama: String? = null,

	@field:SerializedName("Phone")
	val phone: String? = null,

	@field:SerializedName("Photo")
	val photo: String? = null,

	@field:SerializedName("ID")
	val iD: String? = null,

	@field:SerializedName("Thumbnail")
	val thumbnail: String? = null,

	@field:SerializedName("Nominal")
	val nominal: String? = null,

	@field:SerializedName("Timestamp")
	val timestamp: String? = null,

	@field:SerializedName("IDrecord")
	val iDrecord: Int? = null
)
