package com.mobile.ewallet.feature.home

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.mobile.ewallet.base.BaseViewModel
import com.mobile.ewallet.data.DataManager
import com.mobile.ewallet.model.api.TransactionItem
import com.mobile.ewallet.model.api.profile.ProfileAPIResponse
import timber.log.Timber
import javax.inject.Inject

@SuppressLint("CheckResult")
class HomeViewModel
@Inject constructor(private val dataManager: DataManager) : BaseViewModel(){

    internal var onProfileLoaded = MutableLiveData<ProfileAPIResponse>()
    internal var warningMessage = MutableLiveData<String>()

    fun loadProfile() {
        dataManager.loadUserProfile()
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            if(response.isNotEmpty()){
                                if(response[0].status == "1"){
                                    onProfileLoaded.postValue(response[0])
                                }else{
                                    warningMessage.postValue(response[0].message!!)
                                }
                            }else{
                                warningMessage.postValue("empty data profile")
                            }
                        }
                    } else {
                        // not 20x
                        val code = res.code()
                        Timber.w(Throwable("Server Error $code, ${res.message()}"))
                        warningMessage.postValue(res.message())
                    }
                },
                { err ->
                    Timber.e(err)
                    warningMessage.postValue(err.message)
                }
            )
    }

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

    internal fun isLoggedIn(): Boolean = dataManager.getLoginState()

}