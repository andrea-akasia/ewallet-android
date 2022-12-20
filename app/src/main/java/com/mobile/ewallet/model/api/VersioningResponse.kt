package com.mobile.ewallet.model.api

import com.google.gson.annotations.SerializedName

data class VersioningResponse(

	@field:SerializedName("AppsName")
	val appsName: String? = null,

	@field:SerializedName("LinkDownload")
	val linkDownload: String? = null,

	@field:SerializedName("ID")
	val iD: Int? = null,

	@field:SerializedName("ReleaseDate")
	val releaseDate: String? = null,

	@field:SerializedName("VersionNumber")
	val versionNumber: String? = null,

	@field:SerializedName("Logo")
	val logo: String? = null
)
