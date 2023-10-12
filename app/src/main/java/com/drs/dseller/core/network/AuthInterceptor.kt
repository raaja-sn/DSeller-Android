package com.drs.dseller.core.network

import com.drs.dseller.core.domain.repository.AuthRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

/**
 *  OkHttp Interceptor to add the authorization token to each request header.
 *  The interceptor proceeds with the initial request with an id token and if an unauthorized response (401)
 *  is received a new request is processed with updated auth token.
 *
 *  @param authRepository The object which maintains the user Auth session. Usually a session manager or token manager.
 */
class AuthInterceptor @Inject constructor(
    private val authRepository: AuthRepository
) : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val tokenId = getAuthToken(false)
        val newRequest = getRequestWithAuthToken(request,tokenId)
        val response = chain.proceed(newRequest)
        return if(response.code == 401){
            val requestWithNewToken =  getRequestWithAuthToken(
                request,
                getAuthToken(true)
            )
            chain.proceed(requestWithNewToken)
        }else{
            response
        }
    }

    private fun getRequestWithAuthToken(request:Request, tokenId:String):Request{
        return request.newBuilder().apply {
            this.addHeader("Authorization",tokenId)
        }.build()
    }

    private fun getAuthToken(shouldRefresh:Boolean):String{
        return runBlocking {
            authRepository.getAuthToken(shouldRefresh)
        }
    }
}