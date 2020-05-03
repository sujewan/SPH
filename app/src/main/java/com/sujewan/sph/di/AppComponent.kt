package com.sujewan.sph.di

import android.app.Application
import com.sujewan.sph.MyApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [(AndroidSupportInjectionModule::class),
    (ActivityModule::class),
    (ViewModelModule::class),
    (AppModule::class),
    (NetworkModule::class)])
interface AppComponent {

    // Create an instance of the Component
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): AppComponent.Builder
        @BindsInstance
        fun baseUrl(url : String) : AppComponent.Builder
        fun build(): AppComponent
    }

    fun inject(instance: MyApplication)
}