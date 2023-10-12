package com.drs.dseller.core.domain.usecases

import javax.inject.Inject

data class UserSessionUseCases @Inject constructor(
    val getUser: GetUser,
    val logoutUser: LogoutUser
) {
}