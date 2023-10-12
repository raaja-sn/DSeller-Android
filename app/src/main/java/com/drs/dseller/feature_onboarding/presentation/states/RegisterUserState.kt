package com.drs.dseller.feature_onboarding.presentation.states

import com.drs.dseller.core.constants.AppConstants
import com.drs.dseller.core.utils.EmailMatcher
import com.drs.dseller.core.utils.PasswordMatcher
import com.drs.dseller.core.domain.model.AppUserWithPassword

/**
 * The UI state for the Register user screen
 */
data class RegisterUserState(
    val name:String = "",
    val email:String = "",
    val phoneNumber:String = "",
    val password:String = "",
    val isRegistering:Boolean = false,
    val shouldVerify:Boolean = false,
    val type:Int = AppConstants.USER_TYPE_SHOPPER,
    val errorState:OnBoardingErrorState = OnBoardingErrorState(),
    val isRegistrationComplete:Boolean = false
) {

}

fun RegisterUserState.toAppUser(): AppUserWithPassword {
    return AppUserWithPassword(
        this.name,
        this.email,
        this.password,
        "+91${this.phoneNumber}",
        this.type
    )
}

fun RegisterUserState.hasValidUserName():Boolean{
    return name.isNotEmpty() || name.length >= 4
}

fun RegisterUserState.hasValidEmail():Boolean{
    return EmailMatcher.match(email)
}

fun RegisterUserState.hasValidPhoneNumber():Boolean{
    return phoneNumber.length == 10
}

fun RegisterUserState.hasValidPassword():Boolean{
    return PasswordMatcher.match(password)
}

