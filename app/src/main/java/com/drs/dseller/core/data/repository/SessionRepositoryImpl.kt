package com.drs.dseller.core.data.repository

import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.exceptions.SignedOutException
import com.amplifyframework.kotlin.core.Amplify
import com.drs.dseller.core.constants.AppConstants
import com.drs.dseller.core.domain.model.AppUser
import com.drs.dseller.core.domain.repository.SessionRepository
import com.drs.dseller.core.response.SessionResponse
import java.lang.Exception

class SessionRepositoryImpl : SessionRepository<SessionResponse<AppUser>> {

    override suspend fun logoutUser(): SessionResponse<AppUser> {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(): SessionResponse<AppUser> {
        return try{
            val userAttrs = Amplify.Auth.fetchUserAttributes()
            var fullName = ""
            var email = ""
            var phoneNumber = ""
            var dsellerId = ""
            for(attrs in userAttrs){
                if(attrs.key == AuthUserAttributeKey.name()) fullName = attrs.value
                if(attrs.key == AuthUserAttributeKey.email()) email = attrs.value
                if(attrs.key == AuthUserAttributeKey.phoneNumber()) phoneNumber = attrs.value
                if(attrs.key == AuthUserAttributeKey.custom(AppConstants.D_SELLER_USER_ID_ATTRIBUTE_KEY)) dsellerId = attrs.value

            }
            SessionResponse.Success(
                AppUser(
                fullName,
                email,
                phoneNumber,
                dsellerId
            )
            )
        }catch(e:Exception){
            when(e){
                is SignedOutException ->{
                    SessionResponse.SignInError
                }
                else -> SessionResponse.Error(e.message?:"")
            }
        }
    }
}