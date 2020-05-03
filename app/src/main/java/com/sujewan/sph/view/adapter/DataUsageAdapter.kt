package com.sujewan.sph.view.adapter

import android.view.View
import com.sujewan.sph.R
import com.sujewan.sph.model.YearlyRecord
import com.sujewan.sph.view.viewholder.BaseViewHolder
import com.sujewan.sph.view.viewholder.DataUsageViewHolder
import java.util.*

class DataUsageAdapter(val delegate: DataUsageViewHolder.Delegate) : BaseAdapter() {

    init {
        addItems(ArrayList<YearlyRecord>())
    }

    fun updateList(records : List<YearlyRecord>) {
        addItems(records)
        notifyItemInserted(items.size)
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return DataUsageViewHolder(view, delegate)
    }

    override fun layout(item: Any?): Int {
        return R.layout.row_data_usage
    }
}
