package com.drs.dseller.feature_onboarding.presentation.states

import com.drs.dseller.core.constants.AppConstants
import com.drs.dseller.core.domain.model.AppUserWithPassword

data class LoginUserState(
    val email:String="",
    val password:String="",
    val type:Int = AppConstants.USER_TYPE_SHOPPER,
    val isLoggingIn:Boolean = false,
    val isLoginComplete:Boolean = false,
    val errorState:OnBoardingErrorState = OnBoardingErrorState()
) {
}

fun LoginUserState.getAppUser(): AppUserWithPassword {
    return AppUserWithPassword(
        email = this.email,
        password = this.password
    )
}