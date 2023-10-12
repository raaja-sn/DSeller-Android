package com.drs.dseller.feature_onboarding.domain.usecases

import javax.inject.Inject

/**
 *  Use cases to onboard users
 */
data class OnBoardingUseCases @Inject constructor(
    val registerUser: RegisterUser,
    val confirmRegistrationCode: ConfirmRegistrationCode,
    val loginUser:LoginUser
) {
}