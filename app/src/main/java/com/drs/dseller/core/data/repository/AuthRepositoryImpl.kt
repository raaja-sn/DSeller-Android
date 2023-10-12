package com.drs.dseller.core.data.repository

import com.amplifyframework.auth.cognito.AWSCognitoAuthSession
import com.amplifyframework.kotlin.core.Amplify
import com.drs.dseller.core.domain.repository.AuthRepository
import java.lang.Exception

class AuthRepositoryImpl: AuthRepository {

    private var tokenId = ""

    override suspend fun getAuthToken(shouldRefresh: Boolean): String {
        if(!shouldRefresh){
            return tokenId
        }
        tokenId = try{
            val response = Amplify.Auth.fetchAuthSession() as AWSCognitoAuthSession
            response.userPoolTokensResult.value?.idToken?:""
        }catch(e: Exception){
            ""
        }
        return tokenId
    }
}