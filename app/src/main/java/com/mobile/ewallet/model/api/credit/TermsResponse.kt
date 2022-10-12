package com.mobile.ewallet.model.api.credit

import com.google.gson.annotations.SerializedName

data class TermsResponse(
    @field:SerializedName("SyaratKetentuanPendanaan")
    var terms: String = "",
)