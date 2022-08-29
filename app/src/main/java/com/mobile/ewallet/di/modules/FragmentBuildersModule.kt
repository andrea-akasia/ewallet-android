package com.mobile.ewallet.di.modules

import com.mobile.ewallet.feature.detailpokemon.basestat.BaseStatFragment
import com.mobile.ewallet.feature.detailpokemon.moves.MovesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeBaseStatFragment(): BaseStatFragment

    @ContributesAndroidInjector
    abstract fun contributeMovesFragment(): MovesFragment

}