package com.drs.dseller.core.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Inject

class ServiceGenerator @Inject constructor(
    private val okHttpBuilder: OkHttpClient.Builder,
    private val retroFitBuilder : Retrofit.Builder,
    private val authInterceptor: AuthInterceptor
) {

    fun <N> generateService(
        serviceClass:Class<N>,
    ):N {
        val httpClient = okHttpBuilder.addInterceptor(authInterceptor)
        val retrofit = retroFitBuilder.client(httpClient.build()).build()
        return retrofit.create(serviceClass)
    }

}