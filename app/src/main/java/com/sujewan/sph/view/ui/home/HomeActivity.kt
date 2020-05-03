package com.sujewan.sph.view.ui.home

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sujewan.sph.R
import com.sujewan.sph.api.Resource
import com.sujewan.sph.api.Status
import com.sujewan.sph.databinding.ActivityHomeBinding
import com.sujewan.sph.factory.AppViewModelFactory
import com.sujewan.sph.model.YearlyRecord
import com.sujewan.sph.utils.CommonUtil
import com.sujewan.sph.view.adapter.DataUsageAdapter
import com.sujewan.sph.view.ui.general.BaseAppCompatActivity
import com.sujewan.sph.view.viewholder.DataUsageViewHolder
import dagger.android.AndroidInjection
import javax.inject.Inject

class HomeActivity : BaseAppCompatActivity(), DataUsageViewHolder.Delegate {
    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityHomeBinding>(this, R.layout.activity_home) }

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(HomeActivityViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            initView()
            observeViewModel()
        }

        if (!CommonUtil.isNetworkAvailable(this@HomeActivity)) {
            showSnackBar(binding.homeRecordsContainer, getString(R.string.no_internet))
        }
    }

    private fun initView() {
        val linearLayout = androidx.recyclerview.widget.LinearLayoutManager(this)
        binding.rvRecords.layoutManager = linearLayout
        viewModel.adapter = DataUsageAdapter(this)
        binding.rvRecords.adapter = viewModel.adapter
    }

    private fun observeViewModel() {
        viewModel.recordsLiveData.observe(this, Observer { it?.let { processResponse(it) } })
    }

    private fun processResponse(response: Resource<List<YearlyRecord>>) {
        when (response.status) {
            Status.LOADING -> {
                binding.rowLoadingAnim.visibility = View.VISIBLE
            }

            Status.SUCCESS -> {
                renderList(response.data as ArrayList<YearlyRecord>)
            }

            Status.ERROR -> {binding.rowLoadingAnim.visibility = View.GONE
                showWarningDialog(response.error?.localizedMessage
                    ?: getString(R.string.unable_to_connect_server))
            }
        }
    }

    private fun renderList(records: ArrayList<YearlyRecord>) {
        binding.rowLoadingAnim.visibility = View.GONE
        binding.rvRecords.visibility = View.VISIBLE
        viewModel.adapter.updateList(records)
    }

    override fun onItemClick(yearlyRecord: YearlyRecord, view: View) {
        showBreakdownDialog(this@HomeActivity, yearlyRecord)
    }
}
