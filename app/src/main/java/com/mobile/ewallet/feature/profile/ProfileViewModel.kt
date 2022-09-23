package com.mobile.ewallet.feature.profile

import com.mobile.ewallet.base.BaseViewModel
import com.mobile.ewallet.data.DataManager
import javax.inject.Inject

class ProfileViewModel
@Inject constructor(private val dataManager: DataManager) : BaseViewModel(){

    fun logout(){
        dataManager.clearPrefs()
    }

}