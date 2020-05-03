package com.sujewan.sph.di

import com.sujewan.sph.view.ui.home.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Attach our Activities to Dagger Graph
 */
@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    internal abstract fun contributeHomeActivity(): HomeActivity
}