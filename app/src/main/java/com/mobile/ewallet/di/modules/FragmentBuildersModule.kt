package com.mobile.ewallet.di.modules

import com.mobile.ewallet.feature.credit.detail.kum.DetailKUMDocumentsFragment
import com.mobile.ewallet.feature.credit.detail.kum.DetailKUMFulfillmentFragment
import com.mobile.ewallet.feature.credit.detail.kum.DetailKUMPrescreeningFragment
import com.mobile.ewallet.feature.detailpokemon.basestat.BaseStatFragment
import com.mobile.ewallet.feature.detailpokemon.moves.MovesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeDetailKUMDocumentsFragment(): DetailKUMDocumentsFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailKUMFulfillmentFragment(): DetailKUMFulfillmentFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailKUMPrescreeningFragment(): DetailKUMPrescreeningFragment

    @ContributesAndroidInjector
    abstract fun contributeBaseStatFragment(): BaseStatFragment

    @ContributesAndroidInjector
    abstract fun contributeMovesFragment(): MovesFragment

}