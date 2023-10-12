package com.drs.dseller.feature_user_session

import com.drs.dseller.core.domain.usecases.GetUser
import com.drs.dseller.core.domain.usecases.LogoutUser
import com.drs.dseller.core.domain.usecases.UserSessionUseCases
import org.mockito.kotlin.mock

class SessionMocks {

    fun getUseCases(): UserSessionUseCases {
        val getUser: GetUser = mock()
        val logoutUser: LogoutUser = mock()
        return UserSessionUseCases(getUser, logoutUser)
    }
}