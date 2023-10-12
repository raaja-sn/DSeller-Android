package com.drs.dseller.feature_onboarding

import com.drs.dseller.feature_onboarding.domain.usecases.ConfirmRegistrationCode
import com.drs.dseller.feature_onboarding.domain.usecases.LoginUser
import com.drs.dseller.feature_onboarding.domain.usecases.OnBoardingUseCases
import com.drs.dseller.feature_onboarding.domain.usecases.RegisterUser
import org.mockito.kotlin.mock

class OnBoardingMocks {

    fun getUseCase():OnBoardingUseCases{
        val registerUser: RegisterUser = mock()
        val loginUser: LoginUser = mock()
        val confirmRegistrationCode: ConfirmRegistrationCode = mock()
        return OnBoardingUseCases(registerUser,confirmRegistrationCode,loginUser)
    }

}