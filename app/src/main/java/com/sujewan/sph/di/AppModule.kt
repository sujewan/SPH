package com.sujewan.sph.di

import android.app.Application
import androidx.room.Room
import com.sujewan.sph.room.AppDatabase
import com.sujewan.sph.room.YearlyRecordDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application.applicationContext, AppDatabase::class.java, "SPHMobileDataUsage.db")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideYearlyRecordDao(database: AppDatabase): YearlyRecordDao {
        return database.yearlyRecordDao()
    }
}