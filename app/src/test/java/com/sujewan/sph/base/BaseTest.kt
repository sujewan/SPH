package com.sujewan.sph.base

import com.sujewan.sph.api.DataUsageApi
import com.sujewan.sph.api.LiveDataCallAdapterFactory
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.HttpURLConnection
import java.nio.charset.StandardCharsets

/**
 *  Configure MockWebServer library to mock the API response
 */
abstract class BaseTest {

    protected lateinit var service: DataUsageApi
    protected lateinit var mockWebServer: MockWebServer

    @Throws(IOException::class)
    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(DataUsageApi::class.java)
    }

    @Throws(IOException::class)
    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Throws(IOException::class)
    protected fun enqueueResponse(fileName: String) {
        enqueueResponse(fileName, emptyMap(), HttpURLConnection.HTTP_OK)
    }

    @Throws(IOException::class)
    protected fun enqueueResponse(fileName: String, responseCode: Int) {
        enqueueResponse(fileName, emptyMap(), responseCode)
    }

    @Throws(IOException::class)
    private fun enqueueResponse(fileName: String, headers: Map<String, String>, responseCode: Int) {
        val inputStream = javaClass.classLoader?.getResourceAsStream("api-response/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse
                .setBody(source.readString(StandardCharsets.UTF_8))
                .setResponseCode(responseCode)
        )
    }
}