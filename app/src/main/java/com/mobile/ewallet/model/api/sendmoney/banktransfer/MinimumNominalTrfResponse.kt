package com.mobile.ewallet.model.api.sendmoney.banktransfer

import com.google.gson.annotations.SerializedName

data class MinimumNominalTrfResponse(

	@field:SerializedName("NamaRekening")
	val namaRekening: String? = null,

	@field:SerializedName("NomorRekening")
	val nomorRekening: String? = null,

	@field:SerializedName("Photo")
	val photo: String? = null,

	@field:SerializedName("Thumbnail")
	val thumbnail: String? = null,

	@field:SerializedName("NamaBank")
	val namaBank: String? = null,

	@field:SerializedName("Minimal_Pengiriman")
	val minimalPengiriman: String? = null,

	@field:SerializedName("Minimal_PengirimanRp")
	val minimalPengirimanRp: String? = null
)
