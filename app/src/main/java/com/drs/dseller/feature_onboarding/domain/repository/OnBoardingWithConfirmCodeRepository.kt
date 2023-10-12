package com.drs.dseller.feature_onboarding.domain.repository

import com.drs.dseller.core.domain.model.AppUserWithPassword

/**
 * Extension of the [OnBoardingRepository] with verification code confirmation like new registration or OTP logins
 * @param U The user object for login or registration, containing mandatory fields like name,email and phone number
 * @param R The result of on boarding operation . Preferably sealed class with success and error results.
 */
interface OnBoardingWithConfirmCodeRepository<U,R> : OnBoardingRepository<U,R> {

    /**
     * Confirm the verification code for new registration
     *
     * @param userName The username of the user
     * @param code Verification code to verify.
     *
     * @return [R] The result of the code confirmation event
     */
    suspend fun confirmCodeForRegistration(user: AppUserWithPassword, code:String):R

}