package com.flatrocktech.repositoryapp.util.network

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .header(name = "Accept", value = "application/vnd.github.v3+json")
            .build()

        return chain.proceed(request)
    }
}