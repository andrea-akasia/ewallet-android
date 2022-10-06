package com.mobile.ewallet.model.api.sendmoney.banktransfer

import com.google.gson.annotations.SerializedName

open class BaseTransferMoneyResponse(

    @field:SerializedName("NamaBank")
    var bank: String = "",

    @field:SerializedName("NamaRekening")
    var name: String = "",

    @field:SerializedName("Thumbnail")
    var thumbnail: String = "",

    @field:SerializedName("Photo")
    var photo: String = "",

    @field:SerializedName("NomorRekening")
    var norek: String = ""
)