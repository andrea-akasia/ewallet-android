package com.mobile.ewallet.model.api.credit

data class KUMPrescreeningBody(
    var namaPelapor: String = "",
    var nomorKK: String = "",
    var tempatLahir: String = "",
    var codeJenisKelamin: String = "",
    var tanggalLahir: String = "",
    var codePendidikan: String = ""
)