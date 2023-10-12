package com.drs.dseller.feature_onboarding.di

import com.drs.dseller.core.domain.model.AppUserWithPassword
import com.drs.dseller.core.network.ServiceGenerator
import com.drs.dseller.feature_onboarding.data.repository.OnBoardingWithConfirmCodeImpl
import com.drs.dseller.feature_onboarding.domain.repository.OnBoardingRepository
import com.drs.dseller.feature_onboarding.domain.repository.OnBoardingWithConfirmCodeRepository
import com.drs.dseller.feature_onboarding.response.OnBoardingResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class OnBoardingModule {

    @Provides
    fun createOnBoardingWithConfirmCodeRepo(
        serviceGenerator:ServiceGenerator
    )
    :OnBoardingWithConfirmCodeRepository<AppUserWithPassword, OnBoardingResponse<Unit>>{
        return OnBoardingWithConfirmCodeImpl(serviceGenerator)
    }

    @Provides
    fun createOnBoardingRepo(
        repo:OnBoardingWithConfirmCodeRepository<AppUserWithPassword, OnBoardingResponse<Unit>>
    ):OnBoardingRepository<AppUserWithPassword, OnBoardingResponse<Unit>>{
        return repo
    }
}

