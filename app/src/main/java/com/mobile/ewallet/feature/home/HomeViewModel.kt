package com.mobile.ewallet.feature.home

import com.mobile.ewallet.base.BaseViewModel
import com.mobile.ewallet.data.DataManager
import com.mobile.ewallet.model.api.TransactionItem
import javax.inject.Inject

class HomeViewModel
@Inject constructor(private val dataManager: DataManager) : BaseViewModel(){

    fun dummyData(): MutableList<TransactionItem>{
        val result = mutableListOf<TransactionItem>()
        result.add(TransactionItem(type = "IN"))
        result.add(TransactionItem(type = "OUT"))
        result.add(TransactionItem(type = "PENDING"))
        result.add(TransactionItem(type = "IN"))
        result.add(TransactionItem(type = "IN"))
        result.add(TransactionItem(type = "IN"))
        result.add(TransactionItem(type = "IN"))
        result.add(TransactionItem(type = "OUT"))
        return result
    }

}