package com.sujewan.sph.view.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sujewan.sph.api.Resource
import com.sujewan.sph.model.YearlyRecord
import com.sujewan.sph.repository.DataUsageRepository
import com.sujewan.sph.utils.Constants
import com.sujewan.sph.view.adapter.DataUsageAdapter

class HomeActivityViewModel(private val repository: DataUsageRepository) : ViewModel() {
    lateinit var adapter : DataUsageAdapter

    var recordsLiveData: LiveData<Resource<List<YearlyRecord>>> = MutableLiveData()

    init {
        recordsLiveData = repository.getMobileDataUsage(Constants.RESOURCE_ID)
    }
}
