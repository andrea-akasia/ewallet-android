package com.mobile.ewallet.di.modules

import com.mobile.ewallet.feature.auth.*
import com.mobile.ewallet.feature.credit.CreditDetailActivity
import com.mobile.ewallet.feature.credit.kum.KUMPrescreeningActivity
import com.mobile.ewallet.feature.listpokemon.ListPokemonActivity
import com.mobile.ewallet.feature.detailpokemon.DetailPokemonActivity
import com.mobile.ewallet.feature.home.BadgeActivity
import com.mobile.ewallet.feature.home.HomeActivity
import com.mobile.ewallet.feature.home.TransactionDetailActivity
import com.mobile.ewallet.feature.moneyreq.MoneyRequestActivity
import com.mobile.ewallet.feature.moneysend.ContactListActivity
import com.mobile.ewallet.feature.moneysend.MoneySendBankActivity
import com.mobile.ewallet.feature.moneysend.MoneySendTypeActivity
import com.mobile.ewallet.feature.pay.PayInputActivity
import com.mobile.ewallet.feature.pay.PayResultActivity
import com.mobile.ewallet.feature.profile.ProfileActivity
import com.mobile.ewallet.feature.profile.UpdateProfileActivity
import com.mobile.ewallet.feature.scantosendmoney.ScannerActivity
import com.mobile.ewallet.feature.topup.TopupActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule{
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun bindKUMPrescreeningActivity(): KUMPrescreeningActivity

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun bindTopupActivity(): TopupActivity

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun bindContactListActivity(): ContactListActivity

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun bindScannerActivity(): ScannerActivity

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun bindCreditDetailActivity(): CreditDetailActivity

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun bindBadgeActivity(): BadgeActivity

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun bindMoneySendBankActivity(): MoneySendBankActivity

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun bindMoneySendTypeActivity(): MoneySendTypeActivity

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun bindTransactionDetailActivity(): TransactionDetailActivity

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun bindPayResultActivity(): PayResultActivity

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun bindPayInputActivity(): PayInputActivity

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun bindMoneyRequestActivity(): MoneyRequestActivity

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun bindUpdateProfileActivity(): UpdateProfileActivity

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun bindProfileActivity(): ProfileActivity

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun bindHomeActivity(): HomeActivity

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun bindCreateAccountActivity(): CreateAccountActivity

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun bindRegisterActivity(): RegisterActivity

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun bindStartupActivity(): StartupActivity

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun bindAuthActivity(): AuthActivity

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun bindDetailPokemonActivity(): DetailPokemonActivity

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun bindListPokemonActivity(): ListPokemonActivity
}