package com.flatrocktech.repositoryapp.util.network

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .header(name = "Accept", value = "application/vnd.github.v3+json")
            .header(name = "Authorization", value = "token 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")
            .build()

        return chain.proceed(request)
    }
}