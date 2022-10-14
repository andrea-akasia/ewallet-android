package com.mobile.ewallet.model.api.profile

import com.google.gson.annotations.SerializedName

data class TermsCondition(
    @field:SerializedName("SyaratKetentuan")
    var terms: String = "",
)