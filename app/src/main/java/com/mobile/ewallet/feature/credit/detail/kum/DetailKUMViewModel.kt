package com.mobile.ewallet.feature.credit.detail.kum

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.mobile.ewallet.base.BaseViewModel
import com.mobile.ewallet.data.DataManager
import javax.inject.Inject

@SuppressLint("CheckResult")
class DetailKUMViewModel
@Inject constructor(private val dataManager: DataManager) : BaseViewModel() {

    internal var warningMessage = MutableLiveData<String>()

}