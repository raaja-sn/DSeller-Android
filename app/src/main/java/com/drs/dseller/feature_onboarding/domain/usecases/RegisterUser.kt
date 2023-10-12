package com.drs.dseller.feature_onboarding.domain.usecases

import com.drs.dseller.core.domain.model.AppUserWithPassword
import com.drs.dseller.feature_onboarding.domain.repository.OnBoardingRepository
import com.drs.dseller.feature_onboarding.response.OnBoardingResponse
import javax.inject.Inject

/**
 * Register New user
 * @param repo Repository class to register new user
 */
class RegisterUser@Inject constructor(
    private val repo:OnBoardingRepository<AppUserWithPassword, OnBoardingResponse<Unit>>
) {

    suspend operator fun invoke(user: AppUserWithPassword) =
        repo.registerUser(user)
}