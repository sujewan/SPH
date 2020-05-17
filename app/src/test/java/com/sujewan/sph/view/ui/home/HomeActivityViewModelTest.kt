package com.sujewan.sph.view.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.sujewan.sph.api.Resource
import com.sujewan.sph.base.BaseTest
import com.sujewan.sph.model.YearlyRecord
import com.sujewan.sph.repository.DataUsageRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.mockito.Spy
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
@Config(manifest= Config.NONE)
class HomeActivityViewModelTest: BaseTest() {
    private lateinit var viewModel: HomeActivityViewModel
    private val validResourceId = "a807b7ab-6cad-4aa6-87d0-e283a7353a0f"
    private val invalidResourceId = "a807b7ab-6cad-4aa6-87d0-e283a7353a12"

    @Spy
    private val recordsLiveDataSuccess: LiveData<Resource<List<YearlyRecord>>> = MutableLiveData()

    @Spy
    private val recordsLiveDataFailure: LiveData<Resource<List<YearlyRecord>>> = MutableLiveData()

    private lateinit var repository: DataUsageRepository

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        repository = mock()
        runBlocking {
            whenever(repository.getMobileDataUsage(validResourceId)).thenReturn(recordsLiveDataSuccess)
            whenever(repository.getMobileDataUsage(invalidResourceId)).thenReturn(recordsLiveDataFailure)
        }
        viewModel = HomeActivityViewModel(repository)
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `call get mobile data usage records with invalid id`() = runBlocking {
        repository.getMobileDataUsage(invalidResourceId)

        viewModel.recordsLiveData.observeForever{}
        verify(repository).getMobileDataUsage(invalidResourceId)

        assertEquals(recordsLiveDataFailure, repository.getMobileDataUsage(invalidResourceId))
        return@runBlocking
    }

    @Test
    fun `call get mobile data usage records if not present locally`() = runBlocking {
        viewModel.recordsLiveData.observeForever{}
        verify(repository).getMobileDataUsage(validResourceId)

        assertEquals(recordsLiveDataSuccess, repository.getMobileDataUsage(validResourceId))
        return@runBlocking
    }
}