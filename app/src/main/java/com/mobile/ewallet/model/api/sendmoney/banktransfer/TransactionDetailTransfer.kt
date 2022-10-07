package com.mobile.ewallet.model.api.sendmoney.banktransfer

import com.google.gson.annotations.SerializedName

data class TransactionDetailTransfer(
	@field:SerializedName("Status")
	var status: String = "",

	@field:SerializedName("BiayaAdmin")
	var biayaAdmin: String = "",

	@field:SerializedName("NomorReferensi")
	var nomorReferensi: String = "",

	@field:SerializedName("TotalPembayaran")
	var totalPembayaran: String = "",

	@field:SerializedName("TypeKirimUang")
	var typeKirimUang: String = "",

	@field:SerializedName("WaktuTransaksi")
	var waktuTransaksi: String = "",

	@field:SerializedName("Message")
	var message: String = "",

	@field:SerializedName("TypeTransaksi")
	var typeTransaksi: String = "",

	@field:SerializedName("MetodeBayar")
	var metodeBayar: String = "",

	@field:SerializedName("IDTransaksi")
	var iDTransaksi: String = "",

	@field:SerializedName("Jumlah")
	var jumlah: String = ""
): BaseTransferMoneyResponse()
