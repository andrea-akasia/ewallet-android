package com.mobile.ewallet.network

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class BasicAuthInterceptor(
    private val username: String,
    private val password: String
    ): Interceptor {
    private var credentials: String? = null

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val authenticatedRequest: Request = request.newBuilder()
            .header("Authorization", Credentials.basic(username, password)).build()
        return chain.proceed(authenticatedRequest)
    }
}