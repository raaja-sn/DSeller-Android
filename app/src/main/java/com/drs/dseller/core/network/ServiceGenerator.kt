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

    private val okHttpClient = okHttpBuilder.addInterceptor(authInterceptor).build()
    private val retrofit = retroFitBuilder.client(okHttpClient).build()
    fun <N> generateService(
        serviceClass:Class<N>,
    ):N {
        return retrofit.create(serviceClass)
    }

}