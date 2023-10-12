package com.drs.dseller.feature_onboarding.presentation.states

data class ConfirmUserState(
    val confirmationCode:String = "",
    val isConfirming:Boolean = false,
    val isCodeConfirmationComplete:Boolean = false,
    val errorState:OnBoardingErrorState = OnBoardingErrorState()
) {
}