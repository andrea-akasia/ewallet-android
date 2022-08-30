package com.mobile.ewallet.di.modules

import com.mobile.ewallet.feature.auth.AuthActivity
import com.mobile.ewallet.feature.auth.LoginActivity
import com.mobile.ewallet.feature.auth.RegisterActivity
import com.mobile.ewallet.feature.auth.StartupActivity
import com.mobile.ewallet.feature.listpokemon.ListPokemonActivity
import com.mobile.ewallet.feature.detailpokemon.DetailPokemonActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule{
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