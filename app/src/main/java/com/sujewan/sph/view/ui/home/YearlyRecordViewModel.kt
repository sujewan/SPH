package com.sujewan.sph.view.ui.home

import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.sujewan.sph.MyApplication
import com.sujewan.sph.R
import com.sujewan.sph.model.YearlyRecord

class YearlyRecordViewModel(private val record: YearlyRecord) : BaseObservable() {
    @Bindable
    fun getYear(): String = record.year.toString()

    @Bindable
    fun getTotalVolume(): String = record.totalVolume.toString()

    @Bindable
    fun getInfoVisibility(): Int = if (record.isDecreasedYear) View.VISIBLE else View.INVISIBLE

    @Bindable
    fun getStripeBackground(): Drawable? =
        ContextCompat.getDrawable(MyApplication.applicationContext(),
                    if (record.isDecreasedYear) R.drawable.dashboard_item_stripe_red_bg
                    else R.drawable.dashboard_item_stripe_green_bg)

}