package com.sujewan.sph.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sujewan.sph.utils.Constants

@Entity(tableName = Constants.TABLE_YEARLY_RECORD)
data class YearlyRecord(
    @PrimaryKey
    @ColumnInfo(name = Constants.RECORD_YEAR)
    var year: Int = 0,

    @ColumnInfo(name = Constants.RECORD_TOTAL_VOLUME)
    var totalVolume: Float = 0.0f,

    @ColumnInfo(name = Constants.RECORD_IS_DECREASED_YEAR)
    var isDecreasedYear: Boolean = false,

    @ColumnInfo(name = Constants.RECORD_QUARTERS)
    var quarters: ArrayList<Quarter?> = arrayListOf()
)