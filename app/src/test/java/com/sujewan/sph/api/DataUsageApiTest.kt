package com.sujewan.sph.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sujewan.sph.base.BaseTest
import com.sujewan.sph.utils.Constants
import okhttp3.mockwebserver.RecordedRequest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class DataUsageApiTest: BaseTest() {

    @Rule
    @JvmField val instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun getMobileDataUsage_Success() {
        enqueueResponse("get_data_usage.json")
        val records = LiveDataTestUtil.getValue(service.getMobileDataUsage(Constants.RESOURCE_ID)).body

        val request: RecordedRequest = mockWebServer.takeRequest()
        MatcherAssert.assertThat(request.path, CoreMatchers.`is`("/action/datastore_search?resource_id=" + Constants.RESOURCE_ID))
        MatcherAssert.assertThat(records, CoreMatchers.notNullValue())

        MatcherAssert.assertThat(records?.result?.records?.get(0)?.id, CoreMatchers.`is`(1))
        MatcherAssert.assertThat(records?.result?.records?.get(21)?.id, CoreMatchers.`is`(22))

        MatcherAssert.assertThat(records?.result?.records?.get(8)?.quarter, CoreMatchers.`is`("2006-Q3"))
        MatcherAssert.assertThat(records?.result?.records?.get(32)?.volume, CoreMatchers.`is`(5.614201f))

        MatcherAssert.assertThat(records?.help, CoreMatchers.`is`("https://data.gov.sg/api/3/action/help_show?name=datastore_search"))
        MatcherAssert.assertThat(records?.success, CoreMatchers.`is`(true))
    }

    @Test
    fun getMobileDataUsage_Failure() {
        enqueueResponse("get_data_usage_error.json")
        val records = LiveDataTestUtil.getValue(service.getMobileDataUsage(Constants.RESOURCE_ID)).body

        val request: RecordedRequest = mockWebServer.takeRequest()
        MatcherAssert.assertThat(request.path, CoreMatchers.`is`("/action/datastore_search?resource_id=" + Constants.RESOURCE_ID))
        MatcherAssert.assertThat(records, CoreMatchers.notNullValue())

        MatcherAssert.assertThat(records?.result, CoreMatchers.nullValue())
        MatcherAssert.assertThat(records?.help, CoreMatchers.`is`("https://data.gov.sg/api/3/action/help_show?name=datastore_search"))
        MatcherAssert.assertThat(records?.success, CoreMatchers.`is`(false))
    }

    @Test
    fun getMobileDataUsage_Empty_Records() {
        enqueueResponse("get_data_usage_empty.json")
        val records = LiveDataTestUtil.getValue(service.getMobileDataUsage(Constants.RESOURCE_ID)).body

        val request: RecordedRequest = mockWebServer.takeRequest()
        MatcherAssert.assertThat(request.path, CoreMatchers.`is`("/action/datastore_search?resource_id=" + Constants.RESOURCE_ID))
        MatcherAssert.assertThat(records, CoreMatchers.notNullValue())

        MatcherAssert.assertThat(records?.result?.records, CoreMatchers.nullValue())
        MatcherAssert.assertThat(records?.help, CoreMatchers.`is`("https://data.gov.sg/api/3/action/help_show?name=datastore_search"))
        MatcherAssert.assertThat(records?.success, CoreMatchers.`is`(true))
    }

    @Test
    fun getMobileDataUsage_Internal_Error() {
        enqueueResponse("get_data_usage.json", HttpURLConnection.HTTP_INTERNAL_ERROR)
        val records = LiveDataTestUtil.getValue(service.getMobileDataUsage(Constants.RESOURCE_ID)).body

        val request: RecordedRequest = mockWebServer.takeRequest()
        MatcherAssert.assertThat(request.path, CoreMatchers.`is`("/action/datastore_search?resource_id=" + Constants.RESOURCE_ID))
        MatcherAssert.assertThat(records, CoreMatchers.nullValue())

        MatcherAssert.assertThat(records?.result?.records, CoreMatchers.nullValue())
        MatcherAssert.assertThat(records?.help, CoreMatchers.nullValue())
        MatcherAssert.assertThat(records?.success, CoreMatchers.nullValue())
    }

    @Test
    fun getMobileDataUsage_Network_Failure() {
        enqueueResponse("get_data_usage.json", HttpURLConnection.HTTP_GATEWAY_TIMEOUT)
        val records = LiveDataTestUtil.getValue(service.getMobileDataUsage(Constants.RESOURCE_ID)).body

        val request: RecordedRequest = mockWebServer.takeRequest()
        MatcherAssert.assertThat(request.path, CoreMatchers.`is`("/action/datastore_search?resource_id=" + Constants.RESOURCE_ID))
        MatcherAssert.assertThat(records, CoreMatchers.nullValue())

        MatcherAssert.assertThat(records?.result?.records, CoreMatchers.nullValue())
        MatcherAssert.assertThat(records?.help, CoreMatchers.nullValue())
        MatcherAssert.assertThat(records?.success, CoreMatchers.nullValue())
    }
}