package com.sujewan.sph

import android.app.Application
import android.content.Context
import com.sujewan.sph.di.appDatabaseModule
import com.sujewan.sph.di.networkModule
import com.sujewan.sph.di.viewModelModule
import com.sujewan.sph.repository.repoModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

open class MyApplication : Application() {

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
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(listOf(networkModule, appDatabaseModule, viewModelModule, repoModule))
        }
    }
}