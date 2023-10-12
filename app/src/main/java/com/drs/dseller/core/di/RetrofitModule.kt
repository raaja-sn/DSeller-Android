package com.drs.dseller.core.di

import com.drs.dseller.core.network.AuthInterceptor
import com.drs.dseller.core.network.Network
import com.drs.dseller.core.network.ServiceGenerator
import com.drs.dseller.core.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Provides
    fun getOkHttpBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
    }

    @Provides
    fun getRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder().apply {
            this.baseUrl(Network.BASE_URI)
            this.addConverterFactory(GsonConverterFactory.create())
        }
    }

    @Provides
    fun getAuthInterceptor(
        authRepo: AuthRepository
    ): AuthInterceptor {
        return AuthInterceptor(authRepo)
    }

    @Provides
    fun getServiceGenerator(
        okhttpBuilder: OkHttpClient.Builder,
        retrofitBuilder: Retrofit.Builder,
        authInterceptor: AuthInterceptor
    ): ServiceGenerator {
        return ServiceGenerator(okhttpBuilder,retrofitBuilder,authInterceptor)
    }
}