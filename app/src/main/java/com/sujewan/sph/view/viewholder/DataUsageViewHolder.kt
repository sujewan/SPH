package com.sujewan.sph.view.viewholder

import android.view.View
import androidx.databinding.DataBindingUtil
import com.sujewan.sph.databinding.RowDataUsageBinding
import com.sujewan.sph.model.YearlyRecord
import com.sujewan.sph.view.ui.home.YearlyRecordViewModel

class DataUsageViewHolder(view: View, val delegate: Delegate) : BaseViewHolder(view) {
    interface Delegate {
        fun onItemClick(yearlyRecord: YearlyRecord, view: View)
    }

    private lateinit var record: YearlyRecord
    private val binding by lazy { DataBindingUtil.bind<RowDataUsageBinding>(view) }

    override fun bindData(data: Any?) {
        if (data is YearlyRecord) {
            record = data
            drawUI()
        }
    }

    private fun drawUI() {
        binding.apply {
            binding?.vModel = YearlyRecordViewModel(record)
            binding?.executePendingBindings()
        }
    }

    override fun onClick(view: View?) {
        if (record.isDecreasedYear) {
            delegate.onItemClick(record, itemView)
        }
    }
}