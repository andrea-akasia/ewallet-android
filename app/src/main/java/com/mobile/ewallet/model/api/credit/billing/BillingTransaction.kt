package com.mobile.ewallet.model.api.credit.billing

import com.google.gson.annotations.SerializedName

data class BillingTransaction(

	@field:SerializedName("trx_id")
	val trxId: String = "",

	@field:SerializedName("IDMember")
	val iDMember: String = "",

	@field:SerializedName("IDPendanaan")
	val iDPendanaan: String = "",

	@field:SerializedName("trx_amount")
	val trxAmount: String = "",

	@field:SerializedName("IDBank")
	val iDBank: String = "",

	@field:SerializedName("NOVA")
	val nOVA: String = "",

	@field:SerializedName("payment_amount")
	val paymentAmount: String = "",

	@field:SerializedName("datetime_payment")
	val datetimePayment: String = "",

	@field:SerializedName("ID")
	val iD: String = "",

	@field:SerializedName("NamaBank")
	val namaBank: String = "",

	@field:SerializedName("Timestamp")
	val timestamp: String = "",

	@field:SerializedName("cumulative_payment_amount")
	val cumulativePaymentAmount: String = ""
)
