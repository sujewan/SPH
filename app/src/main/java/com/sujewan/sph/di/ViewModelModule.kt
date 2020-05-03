package com.sujewan.sph.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sujewan.sph.factory.AppViewModelFactory
import com.sujewan.sph.view.ui.home.HomeActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Providing entries to a multibound map
 * Providing the value (ViewModel) and the key (ViewModelKey)
 */
@Module
internal abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(HomeActivityViewModel::class)
    internal abstract fun bindHomeActivityViewModel(homeActivityViewModel: HomeActivityViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(appViewModelFactory: AppViewModelFactory) : ViewModelProvider.Factory
}