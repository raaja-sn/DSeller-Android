package com.drs.dseller.feature_account.data.repository

import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.kotlin.core.Amplify
import com.drs.dseller.core.domain.model.AppUser
import com.drs.dseller.core.domain.repository.SessionRepository
import com.drs.dseller.core.network.ServiceGenerator
import com.drs.dseller.core.response.SessionResponse
import com.drs.dseller.feature_account.domain.model.AccountUser
import com.drs.dseller.feature_account.domain.repository.AccountRepository
import com.drs.dseller.feature_account.response.AccountResponse

class AccountRepositoryImpl(
    private val userSessionRepository: SessionRepository<SessionResponse<AppUser>>
):AccountRepository<AccountUser,AccountResponse<AccountUser>> {

    override suspend fun updateUser(user: AccountUser): AccountResponse<AccountUser> {
        return try{
            Amplify.Auth.updateUserAttributes(
                attributes = listOf(
                    AuthUserAttribute(AuthUserAttributeKey.name(),user.name),
                    AuthUserAttribute(AuthUserAttributeKey.phoneNumber(),user.phoneNumber)
                )
            )
            invalidateUser()
        }catch(e:Exception){
            AccountResponse.Error(e.message?:"")
        }
    }

    private suspend fun invalidateUser():AccountResponse<AccountUser>{
        return try{
            when(val r = userSessionRepository.getUser(true)){
                is SessionResponse.Error -> {
                    AccountResponse.Error(r.message)
                }
                SessionResponse.SignInError -> {
                    throw(Exception("User not signed In"))
                }
                is SessionResponse.Success -> {
                    AccountResponse.Success(r.data.toAccountUser())
                }
            }
        }catch(e:Exception){
            AccountResponse.Error(e.message?:"")
        }
    }
}