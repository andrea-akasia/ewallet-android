package com.mobile.ewallet.model.contact

data class Contact(val id: String, val name:String) {
    var numbers = ArrayList<String>()
}