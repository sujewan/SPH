package com.sujewan.sph.view.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
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
    private val INVALID_RESOURCE_ID = "a807b7ab-6cad-4aa6-87d0-e283a7353a12"

    @Spy
    private val recordsLiveDataSuccess: LiveData<Resource<List<YearlyRecord>>> = MutableLiveData()

    @Spy
    private val recordsLiveDataFailure: LiveData<Resource<List<YearlyRecord>>> = MutableLiveData()

    @Mock
    private lateinit var repository: DataUsageRepository

    @Mock
    private lateinit var recordObserver: Observer<Resource<List<YearlyRecord>>>

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        runBlocking {
            whenever(repository.getMobileDataUsage(Constants.RESOURCE_ID)).thenReturn(recordsLiveDataSuccess)
            whenever(repository.getMobileDataUsage(INVALID_RESOURCE_ID)).thenReturn(recordsLiveDataFailure)
        }
        viewModel = HomeActivityViewModel(repository)
    }

    @Test
    fun `call get mobile data usage records if not present locally`() = runBlocking {
        viewModel.recordsLiveData.observeForever(recordObserver)
        verify(repository).getMobileDataUsage(Constants.RESOURCE_ID)

        return@runBlocking
    }
}