package com.mobile.ewallet.model.api.credit.detailrequest.kur

import com.google.gson.annotations.SerializedName

data class DetailKUMDocument(

	@field:SerializedName("PhotoSelfie")
	val photoSelfie: String = "",

	@field:SerializedName("PhotoKTP")
	val photoKTP: String = "",

	@field:SerializedName("PhotoNPWP")
	val photoNPWP: String = "",

	@field:SerializedName("PhotoKK")
	val photoKK: String = "",

	@field:SerializedName("SuratPengajuan")
	val suratPengajuan: String = ""
)
