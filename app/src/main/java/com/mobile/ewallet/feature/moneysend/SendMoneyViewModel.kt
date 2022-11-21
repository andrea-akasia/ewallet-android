package com.mobile.ewallet.feature.moneysend

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.mobile.ewallet.base.BaseViewModel
import com.mobile.ewallet.data.DataManager
import com.mobile.ewallet.model.api.AccountItem
import com.mobile.ewallet.model.api.dashboard.DashboardBalance
import com.mobile.ewallet.model.api.dashboard.TransactionItem
import com.mobile.ewallet.model.api.sendmoney.HistoryTransferTransaction
import com.mobile.ewallet.model.api.sendmoney.banktransfer.Bank
import com.mobile.ewallet.model.api.sendmoney.banktransfer.MinimumNominalTrfResponse
import com.mobile.ewallet.model.api.sendmoney.bycontact.ContactUser
import com.mobile.ewallet.model.contact.Contact
import timber.log.Timber
import javax.inject.Inject

@SuppressLint("CheckResult")
class SendMoneyViewModel
@Inject constructor(private val dataManager: DataManager) : BaseViewModel(){

    internal var warningMessage = MutableLiveData<String>()
    internal var onHistoryTransactionLoaded = MutableLiveData<MutableList<HistoryTransferTransaction>>()
    internal var onBankListLoaded = MutableLiveData<MutableList<String>>()
    internal var onTransferBankMinimumNominalLoaded = MutableLiveData<MinimumNominalTrfResponse>()
    internal var onEwalletUserLoaded = MutableLiveData<MutableList<ContactUser>>()

    var banks = mutableListOf<Bank>()
    var selectedBank: Bank? = null
    var localContact = mutableListOf<Contact>()
    var foundContacts = mutableListOf<ContactUser>()

    fun loadEwalletUser(){
        foundContacts.clear()
        dataManager.loadEwalletUser()
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            onEwalletUserLoaded.postValue(response)
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

    fun loadMinimumNominalTransferBank(idBank: String, accountNumber: String){
        dataManager.transferLoadMinimumNominal(idBank, accountNumber)
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            if(response.isNotEmpty()){
                                if(response[0].status == "1"){
                                    onTransferBankMinimumNominalLoaded.postValue(response[0])
                                }else{
                                    warningMessage.postValue(response[0].message)
                                }
                            }else{
                                warningMessage.postValue("data is empty")
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

    fun loadBankList(){
        banks.clear()
        selectedBank = null
        dataManager.loadBankList()
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            if(response.isNotEmpty()){
                                val filtered = response.filter { b -> b.iD != "0" }
                                val bankStrings = mutableListOf<String>()
                                for(bank in filtered){
                                    banks.add(bank)
                                    bankStrings.add(bank.keterangan!!)
                                }
                                if(filtered.isNotEmpty()){ selectedBank = filtered[0] }
                                onBankListLoaded.postValue(bankStrings)
                            }else{
                                warningMessage.postValue("bank data is empty")
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

    fun loadHistoryTransfer(){
        dataManager.loadHistoryTransferTransaction()
            .doOnSubscribe(this::addDisposable)
            .subscribe(
                { res ->
                    if (res.isSuccessful) {
                        res.body()?.let { response ->
                            onHistoryTransactionLoaded.postValue(response)
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

    fun getDummyBank(): MutableList<String>{
        val result = mutableListOf<String>()
        result.add("BCA")
        result.add("BNI")
        result.add("Mandiri")
        result.add("BRI")
        result.add("BTPN")
        return result
    }

}