package com.mobile.ewallet.model.api.badge

import com.google.gson.annotations.SerializedName

data class BadgeStatus(

	@field:SerializedName("SisaSaldo")
	val sisaSaldo: String? = null,

	@field:SerializedName("Badge_Image")
	val badgeImage: String? = null,

	@field:SerializedName("Badge")
	val badge: String? = null,

	@field:SerializedName("Badge_TotalTopup")
	val badgeTotalTopup: String? = null
)
