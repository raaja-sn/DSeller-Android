package com.drs.dseller.feature_onboarding.presentation.states

data class OnBoardingErrorState(
    val message:String = "",
    val isError:Boolean = false
) {
}