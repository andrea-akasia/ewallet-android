package com.mobile.ewallet.feature.moneysend

import com.mobile.ewallet.base.BaseViewModel
import com.mobile.ewallet.data.DataManager
import com.mobile.ewallet.model.api.AccountItem
import javax.inject.Inject

class SendMoneyViewModel
@Inject constructor(private val dataManager: DataManager) : BaseViewModel(){

    fun getDummy(): MutableList<AccountItem>{
        val result = mutableListOf<AccountItem>()
        result.add(AccountItem(name = "Brigadir A"))
        result.add(AccountItem(name = "Brigadir B"))
        result.add(AccountItem(name = "Brigadir C"))
        result.add(AccountItem(name = "Brigadir D"))
        result.add(AccountItem(name = "Brigadir E"))
        result.add(AccountItem(name = "Brigadir F"))
        result.add(AccountItem(name = "Brigadir G"))
        result.add(AccountItem(name = "Brigadir H"))
        result.add(AccountItem(name = "Brigadir I"))
        return result
    }

}