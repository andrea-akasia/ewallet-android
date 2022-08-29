package com.mobile.ewallet.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection
import javax.inject.Inject

abstract class BaseComposeActivity<T : BaseViewModel> : ComponentActivity(){

    @Inject lateinit var factory: ViewModelProvider.Factory
    protected lateinit var viewModel: T

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, factory)[viewModelClass]
    }

    protected abstract val viewModelClass: Class<T>
}