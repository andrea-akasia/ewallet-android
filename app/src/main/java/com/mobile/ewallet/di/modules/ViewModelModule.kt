package com.mobile.ewallet.di.modules

import dagger.Module
import androidx.lifecycle.ViewModel
import com.mobile.ewallet.di.scopes.ViewModelKey
import com.mobile.ewallet.feature.listpokemon.ListPokemonViewModel
import dagger.multibindings.IntoMap
import dagger.Binds
import com.mobile.ewallet.base.AppViewModelFactory
import androidx.lifecycle.ViewModelProvider
import com.mobile.ewallet.feature.profile.ProfileViewModel
import com.mobile.ewallet.feature.auth.AuthViewModel
import com.mobile.ewallet.feature.credit.CreditViewModel
import com.mobile.ewallet.feature.credit.kum.FulfillmentViewModel
import com.mobile.ewallet.feature.credit.kum.KUMDocumentViewModel
import com.mobile.ewallet.feature.credit.kur.KURCreditViewModel
import com.mobile.ewallet.feature.credit.kur.KURDocumentViewModel
import com.mobile.ewallet.feature.credit.kur.KURFulfillmentViewModel
import com.mobile.ewallet.feature.detailpokemon.DetailPokemonViewModel
import com.mobile.ewallet.feature.detailpokemon.basestat.BaseStatViewModel
import com.mobile.ewallet.feature.detailpokemon.moves.MovesViewModel
import com.mobile.ewallet.feature.home.HomeViewModel
import com.mobile.ewallet.feature.moneyreq.MoneyRequestViewModel
import com.mobile.ewallet.feature.moneysend.SendMoneyViewModel
import com.mobile.ewallet.feature.pay.PayViewModel
import com.mobile.ewallet.feature.topup.TopupViewModel

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(KURDocumentViewModel::class)
    abstract fun bindKURDocumentViewModel(kurDocumentViewModel: KURDocumentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(KURFulfillmentViewModel::class)
    abstract fun bindKURFulfillmentViewModel(kurFulfillmentViewModel: KURFulfillmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(KURCreditViewModel::class)
    abstract fun bindKURCreditViewModel(kurCreditViewModel: KURCreditViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(KUMDocumentViewModel::class)
    abstract fun bindKUMDocumentViewModel(kumDocumentViewModel: KUMDocumentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FulfillmentViewModel::class)
    abstract fun bindFulfillmentViewModel(fulfillmentViewModel: FulfillmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TopupViewModel::class)
    abstract fun bindTopupViewModel(topupViewModel: TopupViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreditViewModel::class)
    abstract fun bindCreditViewModel(creditViewModel: CreditViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SendMoneyViewModel::class)
    abstract fun bindSendMoneyViewModel(sendMoneyViewModel: SendMoneyViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PayViewModel::class)
    abstract fun bindPayViewModel(payViewModel: PayViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MoneyRequestViewModel::class)
    abstract fun bindMoneyRequestViewModel(moneyRequestViewModel: MoneyRequestViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileViewModel(profileViewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindAuthViewModel(authViewModel: AuthViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BaseStatViewModel::class)
    abstract fun bindBaseStatViewModel(baseStatViewModel: BaseStatViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovesViewModel::class)
    abstract fun bindMovesViewModel(movesViewModel: MovesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailPokemonViewModel::class)
    abstract fun bindDetailPokemonViewModel(detailPokemonViewModel: DetailPokemonViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ListPokemonViewModel::class)
    abstract fun bindListPokemonViewModel(listPokemonViewModel: ListPokemonViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory
}