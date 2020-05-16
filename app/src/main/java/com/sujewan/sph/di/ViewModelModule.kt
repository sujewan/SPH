package com.sujewan.sph.di

import com.sujewan.sph.view.ui.home.HomeActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeActivityViewModel(get()) }
}