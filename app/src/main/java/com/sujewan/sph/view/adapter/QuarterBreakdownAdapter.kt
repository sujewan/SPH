package com.sujewan.sph.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sujewan.sph.R
import com.sujewan.sph.databinding.RowQuarterBreakdownBinding
import com.sujewan.sph.model.Quarter

class QuarterBreakdownAdapter(private val context: Context,
                              private val quarters: ArrayList<Quarter?>) :
    RecyclerView.Adapter<QuarterBreakdownAdapter.BreakdownViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreakdownViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowQuarterBreakdownBinding.inflate(inflater, parent, false)
        return BreakdownViewHolder(
            binding
        )
    }

    override fun getItemCount() = quarters.size

    override fun onBindViewHolder(holder: BreakdownViewHolder, position: Int) {
        quarters[position]?.let { holder.bind(context, it) }
    }

    class BreakdownViewHolder(rowBinding: RowQuarterBreakdownBinding)
        : RecyclerView.ViewHolder(rowBinding.root) {
        private val binding = rowBinding

        fun bind(context: Context, quarter: Quarter) {
            binding.quarter = quarter
            binding.txtColor = ContextCompat.getColor(context,
                if (quarter.isDecrease) R.color.color_red else R.color.color_green)
            binding.executePendingBindings()
        }
    }
}
