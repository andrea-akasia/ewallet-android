package com.mobile.ewallet.feature.profile

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.mobile.ewallet.base.BaseViewModel
import com.mobile.ewallet.data.DataManager
import com.mobile.ewallet.model.api.profile.ProfileAPIResponse
import com.mobile.ewallet.util.createMultipartFromImageFile
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@SuppressLint("CheckResult")
class ProfileViewModel
@Inject constructor(private val dataManager: DataManager) : BaseViewModel(){

    internal var isProfileLoading = MutableLiveData<Boolean>()
    internal var onProfileLoaded = MutableLiveData<ProfileAPIResponse>()
    internal var warningMessage = MutableLiveData<String>()
    internal var onProfileSaved = MutableLiveData<Boolean>()
    internal var isLoading = MutableLiveData<Boolean>()

    var profileData: ProfileAPIResponse? = null
    var isNeedUpdatePhoto: Boolean = false
    var photoFile: File? = null

    fun save(phone: String, name: String, birthDate: String = ""){
        isLoading.postValue(true)
        if(isNeedUpdatePhoto){
            Timber.i("filepath: ${photoFile!!.path}")
            uploadPhoto(
                file = photoFile!!,
                name = name,
                phone = phone,
                birthDate = birthDate
            )
        }else{
            saveProfile(phone, name, birthDate)
        }
    }

    private fun saveProfile(phone: String, name: String, birthDate: String = ""){
        dataManager.saveProfile(name, phone, birthDate)
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    isLoading.postValue(false)
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            if(response.isNotEmpty()){
                                if(response[0].status == "1"){
                                    onProfileSaved.postValue(true)
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
                    isLoading.postValue(false)
                    Timber.e(err)
                    warningMessage.postValue(err.message)
                }
            )
    }

    private fun uploadPhoto(file: File, phone: String, name: String, birthDate: String = ""){
        isNeedUpdatePhoto = false
        dataManager.uploadPhotoProfile(
            createMultipartFromImageFile(file, "PHOTO_PROFILE")
        )
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    isLoading.postValue(false)
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            if(response.isNotEmpty()){
                                if(response[0].status == "1"){
                                    saveProfile(phone, name, birthDate)
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
                    isLoading.postValue(false)
                    Timber.e(err)
                    warningMessage.postValue(err.message)
                }
            )
    }

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