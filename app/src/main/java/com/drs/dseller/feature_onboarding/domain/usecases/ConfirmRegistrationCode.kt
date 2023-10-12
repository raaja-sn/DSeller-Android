package com.drs.dseller.feature_onboarding.domain.usecases

import com.drs.dseller.core.domain.model.AppUserWithPassword
import com.drs.dseller.feature_onboarding.domain.repository.OnBoardingWithConfirmCodeRepository
import com.drs.dseller.feature_onboarding.response.OnBoardingResponse
import javax.inject.Inject

/**
 * Confirm the registration code
 *
 * @param repo Repository object to perform the operation
 */
class ConfirmRegistrationCode@Inject constructor(
    private val repo:OnBoardingWithConfirmCodeRepository<AppUserWithPassword, OnBoardingResponse<Unit>>
){


    suspend operator fun invoke(user: AppUserWithPassword, code:String) =
        repo.confirmCodeForRegistration(user,code)
}