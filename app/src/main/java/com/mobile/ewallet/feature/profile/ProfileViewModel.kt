package com.mobile.ewallet.feature.profile

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.mobile.ewallet.base.BaseViewModel
import com.mobile.ewallet.data.DataManager
import com.mobile.ewallet.model.api.profile.ProfileAPIResponse
import timber.log.Timber
import javax.inject.Inject

@SuppressLint("CheckResult")
class ProfileViewModel
@Inject constructor(private val dataManager: DataManager) : BaseViewModel(){

    internal var isProfileLoading = MutableLiveData<Boolean>()
    internal var onProfileLoaded = MutableLiveData<ProfileAPIResponse>()
    internal var warningMessage = MutableLiveData<String>()

    var profileData: ProfileAPIResponse? = null

    fun loadProfile() {
        profileData = null
        isProfileLoading.postValue(true)
        dataManager.loadUserProfile()
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    isProfileLoading.postValue(false)
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            if(response.isNotEmpty()){
                                if(response[0].status == "1"){
                                    profileData = response[0]
                                    onProfileLoaded.postValue(profileData)
                                }else{
                                    warningMessage.postValue(response[0].message!!)
                                }
                            }else{
                                warningMessage.postValue("empty data")
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
                    isProfileLoading.postValue(false)
                    Timber.e(err)
                    warningMessage.postValue(err.message)
                }
            )
    }

    fun logout(){
        dataManager.clearPrefs()
    }

}