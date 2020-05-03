package com.sujewan.sph

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.annotation.VisibleForTesting
import com.sujewan.sph.di.DaggerAppComponent
import com.sujewan.sph.utils.Constants
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

open class MyApplication : Application(), HasActivityInjector {
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    init {
        instance = this
    }

    companion object {
        private var instance: MyApplication? = null
        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        setupDagger()
    }

    @VisibleForTesting
    open fun setupDagger() {
        DaggerAppComponent.builder()
            .application(this)
            .baseUrl(Constants.BASE_URL)
            .build()
            .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}