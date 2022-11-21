package com.mobile.ewallet.model.api.sendmoney.banktransfer

import com.google.gson.annotations.SerializedName

data class SendMoneyResultTrfResponse(
	@field:SerializedName("Status")
	val status: String? = null,

	@field:SerializedName("BiayaAdmin")
	val adminFee: String? = null,

	@field:SerializedName("NomorReferensi")
	val reff: String? = null,

	@field:SerializedName("TypeKirimUang")
	val type: String? = null,

	@field:SerializedName("WaktuTransaksi")
	val time: String? = null,

	@field:SerializedName("Message")
	val message: String? = null,

	@field:SerializedName("TypeTransaksi")
	val typeTransaksi: String? = null,

	@field:SerializedName("MetodeBayar")
	val metodeBayar: String? = null,

	@field:SerializedName("TotalPembayaran")
	val total: String? = null,

	@field:SerializedName("IDTransaksi")
	val iDTransaksi: String? = null,

	@field:SerializedName("Jumlah")
	val amount: String? = null
): BaseTransferMoneyResponse()
