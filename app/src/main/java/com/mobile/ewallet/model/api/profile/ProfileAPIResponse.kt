package com.mobile.ewallet.model.api.profile

import com.google.gson.annotations.SerializedName

data class ProfileAPIResponse(

	@field:SerializedName("Status")
	val status: String? = null,

	@field:SerializedName("PhotoProfile_Thumbnail")
	val photoProfileThumbnail: String? = null,

	@field:SerializedName("EKTP_Thumbnail")
	val eKTPThumbnail: String? = null,

	@field:SerializedName("Nama")
	val nama: String? = null,

	@field:SerializedName("Message")
	val message: String? = null,

	@field:SerializedName("IDBadge")
	val iDBadge: Int? = null,

	@field:SerializedName("PhotoProfile_Photo")
	val photoProfilePhoto: String? = null,

	@field:SerializedName("Timestamp")
	val timestamp: String? = null,

	@field:SerializedName("NOWA")
	val nOWA: String? = null,

	@field:SerializedName("BadgeText")
	val badgeText: String? = null,

	@field:SerializedName("IDMember")
	val iDMember: String? = null,

	@field:SerializedName("NIK")
	val nIK: String? = null,

	@field:SerializedName("EKTP_Photo")
	val eKTPPhoto: String? = null,

	@field:SerializedName("ActivationTimestamp")
	val activationTimestamp: String? = null,

	@field:SerializedName("LockData")
	val lockData: Int? = null,

	@field:SerializedName("TanggalLahir")
	val tanggalLahir: String? = null,

	@field:SerializedName("BadgeImage")
	val badgeImage: String? = null
)
