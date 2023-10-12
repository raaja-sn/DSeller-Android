package com.drs.dseller.feature_onboarding.domain.usecases

import com.drs.dseller.core.domain.model.AppUserWithPassword
import com.drs.dseller.feature_onboarding.domain.repository.OnBoardingRepository
import com.drs.dseller.feature_onboarding.response.OnBoardingResponse
import javax.inject.Inject

/**
 * Login a user
 * @param repo Repository object to perform the shopper or seller login
 */
class LoginUser @Inject constructor(
    private val repo:OnBoardingRepository<AppUserWithPassword, OnBoardingResponse<Unit>>
) {

    suspend operator fun invoke(user: AppUserWithPassword) =
        repo.loginUser(user)
}