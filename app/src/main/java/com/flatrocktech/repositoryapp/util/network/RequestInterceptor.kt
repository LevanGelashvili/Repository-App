package com.flatrocktech.repositoryapp.util.network

import okhttp3.Interceptor
import okhttp3.Response

/*
    To generate OAuth Token:
    https://docs.github.com/en/github/authenticating-to-github/keeping-your-account-and-data-secure/creating-a-personal-access-token
 */
class RequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .header(name = "Accept", value = "application/vnd.github.v3+json")
            //.header(name = "Authorization", value = "token YOUR_OAUTH_TOKEN")
            .build()

        return chain.proceed(request)
    }
}