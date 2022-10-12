package com.mobile.ewallet.model.api.dashboard

import com.google.gson.annotations.SerializedName

data class DashboardBalance(

	@field:SerializedName("LimitPinjaman")
	val limitPinjaman: String? = null,

	@field:SerializedName("IDPendanaanDisetujui")
	var iDPendanaanDisetujui: String? = null,

	@field:SerializedName("Last_Topup")
	val lastTopup: String? = null,

	@field:SerializedName("DATA_OUT")
	val dATAOUT: String? = null,

	@field:SerializedName("SisaSaldo")
	val sisaSaldo: String? = null,

	@field:SerializedName("Badge_Image")
	val badgeImage: String? = null,

	@field:SerializedName("DATA_IN")
	val dATAIN: String? = null,

	@field:SerializedName("PinjamanAktif")
	val pinjamanAktif: String? = null,

	@field:SerializedName("Badge")
	val badge: String? = null
)
