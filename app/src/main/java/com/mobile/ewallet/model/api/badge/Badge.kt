package com.mobile.ewallet.model.api.badge

import com.google.gson.annotations.SerializedName

data class Badge(

	@field:SerializedName("Description")
	val description: String? = null,

	@field:SerializedName("ID")
	val iD: Int? = null,

	@field:SerializedName("Badge")
	val badge: String? = null,

	@field:SerializedName("BadgeImage")
	val badgeImage: String? = null
)
