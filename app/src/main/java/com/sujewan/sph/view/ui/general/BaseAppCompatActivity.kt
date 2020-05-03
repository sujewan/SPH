package com.sujewan.sph.view.ui.general

import android.content.Context
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.google.android.material.snackbar.Snackbar
import com.sujewan.sph.R
import com.sujewan.sph.databinding.CustomLayoutInfoBinding
import com.sujewan.sph.databinding.CustomLayoutWarningBinding
import com.sujewan.sph.model.YearlyRecord
import com.sujewan.sph.view.adapter.QuarterBreakdownAdapter


abstract class BaseAppCompatActivity: AppCompatActivity() {
    private var loadingDialog: MaterialDialog? = null

    protected open fun showSnackBar(view: View, message: String?) {
        val snackBar = Snackbar.make(view, message!!, Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction("Dismiss") {
            snackBar.dismiss()
        }.setActionTextColor(ContextCompat.getColor(applicationContext, R.color.color_red)).show()
    }

    protected fun showWarningDialog(warningMessage: String) {
        if (loadingDialog != null && loadingDialog!!.isShowing) {
            loadingDialog!!.dismiss()
        }

        runOnUiThread {
            val binding = CustomLayoutWarningBinding.inflate(layoutInflater)
            loadingDialog = MaterialDialog(this)
                .customView(view = binding.root)
                .cornerRadius(8f)
                .positiveButton(R.string.ok) {}

            binding.lblWarningDialog.text = warningMessage
            loadingDialog!!.cancelable(false)
            loadingDialog!!.cancelOnTouchOutside(false)
            loadingDialog!!.show()
        }
    }

    protected fun showBreakdownDialog(context: Context, record: YearlyRecord) {
        if (loadingDialog != null && loadingDialog!!.isShowing) {
            loadingDialog!!.dismiss()
        }

        runOnUiThread {
            val binding = CustomLayoutInfoBinding.inflate(layoutInflater)
            loadingDialog = MaterialDialog(this)
                .customView(view = binding.root)
                .cornerRadius(8f)

            binding.lblInfoTitle.text = getString(R.string.yearly_usage_breakdown).plus(record.year)
            with(binding.quarterBreakdownList) {
                adapter = QuarterBreakdownAdapter(
                    context,
                    record.quarters
                )
                layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
                setHasFixedSize(true)
            }
            loadingDialog!!.cancelable(true)
            loadingDialog!!.cancelOnTouchOutside(true)
            loadingDialog!!.show()
        }
    }
}
