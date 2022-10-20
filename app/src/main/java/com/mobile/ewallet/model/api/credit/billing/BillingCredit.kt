package com.mobile.ewallet.model.api.credit.billing

import com.google.gson.annotations.SerializedName

data class BillingCredit(

	@field:SerializedName("Status")
	val status: String = "",

	@field:SerializedName("IDMember")
	val iDMember: String = "",

	@field:SerializedName("TanggalExpired")
	val tanggalExpired: String = "",

	@field:SerializedName("TotalTagihan")
	val totalTagihan: String = "",

	@field:SerializedName("IDPendanaan")
	val iDPendanaan: String = "",

	@field:SerializedName("ID")
	val iD: String = "",

	@field:SerializedName("PinjamanAktif")
	val pinjamanAktif: String = "",

	@field:SerializedName("DendaKeterlambatan")
	val dendaKeterlambatan: String = "",

	@field:SerializedName("Bunga")
	val bunga: String = "",

	@field:SerializedName("Timestamp")
	val timestamp: String = "",

	@field:SerializedName("TanggalJatuhTempo")
	val tanggalJatuhTempo: String = ""
)
