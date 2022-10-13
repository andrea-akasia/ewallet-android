package com.mobile.ewallet.model.api.topup

import com.google.gson.annotations.SerializedName

data class TopupViaKreditStatResponse(

	@field:SerializedName("MaksimalNominal_RP")
	val maksimalNominalRP: String = "",

	@field:SerializedName("MaksimalNominal")
	val maksimalNominal: String = "",

	@field:SerializedName("SisaSaldoKredit_RP")
	val sisaSaldoKreditRP: String = "",

	@field:SerializedName("SisaSaldoKredit")
	val sisaSaldoKredit: String = "",

	@field:SerializedName("SisaSaldoKredit1")
	val sisaSaldoKredit1: String = ""
)
