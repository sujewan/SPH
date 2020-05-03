package com.sujewan.sph.view.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.mock
import com.sujewan.sph.api.Resource
import com.sujewan.sph.base.BaseTest
import com.sujewan.sph.model.YearlyRecord
import com.sujewan.sph.repository.DataUsageRepository
import com.sujewan.sph.room.YearlyRecordDao
import com.sujewan.sph.utils.Constants
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Spy
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest= Config.NONE)
class HomeActivityViewModelTest: BaseTest() {
    private lateinit var viewModel: HomeActivityViewModel
    private val yearlyRecordDao = mock<YearlyRecordDao>()

    @Spy
    private val recordsLiveData: LiveData<Resource<List<YearlyRecord>>> = MutableLiveData()

    @Mock
    private lateinit var repository: DataUsageRepository

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        `when`(repository.getMobileDataUsage(Constants.RESOURCE_ID)).thenReturn(recordsLiveData)
        viewModel = HomeActivityViewModel(repository)

    }

    @Test
    fun `call get mobile data usage records if not present locally`() = runBlocking {
        viewModel.recordsLiveData.observeForever{}
        Mockito
            .verify(repository)
            .getMobileDataUsage(Constants.RESOURCE_ID)

        return@runBlocking
    }
}