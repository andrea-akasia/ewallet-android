package com.mobile.ewallet.model.api.sendmoney.bycontact

import com.google.gson.annotations.SerializedName

data class TransactionDetailContact(
	@field:SerializedName("Status")
	val status: String = "",

	@field:SerializedName("BiayaAdmin")
	val biayaAdmin: String = "",

	@field:SerializedName("NomorReferensi")
	val nomorReferensi: String = "",

	@field:SerializedName("TotalPembayaran")
	val totalPembayaran: String = "",

	@field:SerializedName("TypeKirimUang")
	val typeKirimUang: String = "",

	@field:SerializedName("WaktuTransaksi")
	val waktuTransaksi: String = "",

	@field:SerializedName("Message")
	val message: String = "",

	@field:SerializedName("TypeTransaksi")
	val typeTransaksi: String = "",

	@field:SerializedName("NamaBeneficiary")
	val namaBeneficiary: String = "",

	@field:SerializedName("IDTransaksi")
	val iDTransaksi: String = "",

	@field:SerializedName("Photo")
	val photo: String = "",

	@field:SerializedName("Thumbnail")
	val thumbnail: String = "",

	@field:SerializedName("PhoneBeneficiary")
	val phoneBeneficiary: String = "",

	@field:SerializedName("MetodeBayar")
	val metodeBayar: String = "",

	@field:SerializedName("Jumlah")
	val jumlah: String = ""
)
