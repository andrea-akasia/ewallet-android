package com.mobile.ewallet.model.api.credit

import com.google.gson.annotations.SerializedName

data class PendanaanItem(

	@field:SerializedName("StatusProses")
	var statusProses: String? = null,

	@field:SerializedName("NominalPengajuan")
	val nominalPengajuan: String? = null,

	@field:SerializedName("TanggalDisetujui")
	val tanggalDisetujui: String? = null,

	@field:SerializedName("IDRecord")
	val iDRecord: String? = null,

	@field:SerializedName("JangkaWaktu")
	val jangkaWaktu: String? = null,

	@field:SerializedName("Kode")
	val kode: String? = null,

	@field:SerializedName("NominalDisetujui")
	val nominalDisetujui: String? = null,

	@field:SerializedName("ID")
	val iD: String? = null,

	@field:SerializedName("Tanggal")
	val tanggal: String? = null,

	@field:SerializedName("Timestamp")
	val timestamp: String? = null,

	@field:SerializedName("TypePengajuan")
	val typePengajuan: String? = null,

	@field:SerializedName("AlasanReject")
	var alasanReject: String? = null
)
