package com.mobile.ewallet.di.modules

import com.mobile.ewallet.feature.auth.*
import com.mobile.ewallet.feature.listpokemon.ListPokemonActivity
import com.mobile.ewallet.feature.detailpokemon.DetailPokemonActivity
import com.mobile.ewallet.feature.home.HomeActivity
import com.mobile.ewallet.feature.profile.ProfileActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule{
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