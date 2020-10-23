package com.nikhil.petsinfoapp.di

import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * Network module used to provide Okhttp client instance
 */
object NetworkModule {

    var okHttpClient = OkHttpClient()

    /**
     * Get response data from the URL
     * @param url =  url used to fetch the data
     * @param callback - callback triggered after success or failed
     */
     fun getData(url: String, callback: Callback): Call {
        val request = Request.Builder()
            .url(url)
            .build()

        val call = okHttpClient.newCall(request)
        call.enqueue(callback)
        return call
    }
}