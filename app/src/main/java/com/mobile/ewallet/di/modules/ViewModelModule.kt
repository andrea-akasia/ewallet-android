package com.mobile.ewallet.di.modules

import dagger.Module
import androidx.lifecycle.ViewModel
import com.mobile.ewallet.di.scopes.ViewModelKey
import com.mobile.ewallet.feature.listpokemon.ListPokemonViewModel
import dagger.multibindings.IntoMap
import dagger.Binds
import com.mobile.ewallet.base.AppViewModelFactory
import androidx.lifecycle.ViewModelProvider
import com.mobile.ewallet.feature.detailpokemon.DetailPokemonViewModel
import com.mobile.ewallet.feature.detailpokemon.basestat.BaseStatViewModel
import com.mobile.ewallet.feature.detailpokemon.moves.MovesViewModel

@Module
abstract class ViewModelModule {

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