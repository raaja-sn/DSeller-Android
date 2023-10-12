package com.drs.dseller.feature_user_session.presentation.states

import com.drs.dseller.core.domain.model.AppUser

data class SplashScreenState(
    val isVerifyingSession:Boolean = false,
    val shouldVerifySession:Boolean = true,
    val user: AppUser? = null,
    val errorState: SplashScreenErrorState = SplashScreenErrorState(),
    val shouldSignIn:Boolean = false
) {
}