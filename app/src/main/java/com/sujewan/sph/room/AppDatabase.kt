package com.sujewan.sph.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sujewan.sph.model.YearlyRecord

@Database(entities = [(YearlyRecord::class)], version = 1, exportSchema = false)
@TypeConverters(RoomConverters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun yearlyRecordDao() : YearlyRecordDao
}